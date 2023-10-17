package com.example.cloudgateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class CorsGlobalConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("GET", "POST", "OPTIONS")
            .maxAge(3600L)
            .allowCredentials(true)
            .exposedHeaders("Vary", "Content-Type", "Authorization", "Access-Control-Allow-Credentials", "Access-Control-Allow-Origin")
            .allowedHeaders("Vary", "Content-Type", "Authorization", "Access-Control-Allow-Credentials", "Access-Control-Allow-Origin")
        ;
    }

}
