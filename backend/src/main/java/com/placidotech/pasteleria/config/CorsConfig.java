package com.placidotech.pasteleria.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsWebFilter(){
        CorsConfiguration config = new CorsConfiguration();

        //Permite credenciales (ejemplo: cookies, autenticación)
        config.setAllowCredentials(true);

        //Permite peticiones dede el frontend (Vue.js)
        config.setAllowedOrigins(List.of("http://localhost:8080"));
        
        //Permite estos encabezados en las solicitudes
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // Métodos HTTP permitidos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Aplica a todas las rutas

        return new CorsFilter(source);
    }
}
