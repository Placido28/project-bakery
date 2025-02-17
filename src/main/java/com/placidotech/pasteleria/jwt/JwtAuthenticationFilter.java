package com.placidotech.pasteleria.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.placidotech.pasteleria.service.UserDetailsServiceImpl;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtProvider jwtProvider;
    @Autowired
    private  UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Verificar si la URL es pública
        String requestURI = request.getRequestURI();
        if (isPublicEndPoint(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        //Extraer el token del header
        String token = extractToken(request);

        // Si no hay token, continuar sin autenticar
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Validar el token y obtener usuario
            String username = jwtProvider.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            //Crear la autenicación y establecerla en el contexto de seguridad
            UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (JwtException e) {
            //Si el token no es válido, continuar sin autenticación
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o expirado");
            return;
        }

        filterChain.doFilter(request, response);
    }

    //Extrae el token del header "Authorization"
    private String extractToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    //Define las rutas públicas que no requieren autenticación.
    private boolean isPublicEndPoint(String uri){
        return uri.startsWith("/api/cart") || uri.startsWith("/api/products");
    }
}
