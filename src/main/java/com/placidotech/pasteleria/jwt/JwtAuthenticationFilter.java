package com.placidotech.pasteleria.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.placidotech.pasteleria.service.UserDetailsServiceImpl;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtProvider jwtProvider;
    private final  UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, UserDetailsServiceImpl userDetailsService) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        // Verificar si la URL es pública
        String requestURI = request.getRequestURI();

        if (isPublicEndPoint(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Si hay autenticación en el contexto, continuar con la cadena de filtros
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        //Extraer el token del header
        String token = extractToken(request);

        // 🔹 Agregamos logs para depuración
        System.out.println("🔹 Token recibido: " + token);

        // Si no hay token, continuar sin autenticar
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            // Validar el token
            if (!jwtProvider.validateToken(token)) {
                throw new JwtException("Token inválido o expirado");
            }

            // Validar el token y obtener usuario
            String username = jwtProvider.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            System.out.println("🔹 Usuario extraído del token: " + username);
            System.out.println("🔹 Roles obtenidos del usuario: " + userDetails.getAuthorities());

            UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
            System.out.println("✅ Autenticación en SecurityContext después: " + SecurityContextHolder.getContext().getAuthentication());

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
        return uri.startsWith("/api/auth") || uri.startsWith("/api/cart") || uri.startsWith("/api/products");
    }

}