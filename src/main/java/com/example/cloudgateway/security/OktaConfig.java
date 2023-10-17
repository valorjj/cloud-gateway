package com.example.cloudgateway.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static org.springframework.security.config.Customizer.*;

@Configuration
@EnableWebFluxSecurity
public class OktaConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(auth -> auth
            .pathMatchers("/authenticate/login").permitAll()
            .pathMatchers("/oauth2/**").permitAll()
            .anyExchange().authenticated());
        http.oauth2Login(withDefaults());
        http.oauth2ResourceServer(auth -> auth.jwt(withDefaults()));
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();

        //
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(List.of("Vary", "Content-Type", "Authorization", "Access-Control-Allow-Credentials", "Access-Control-Allow-Origin"));
        corsConfig.setExposedHeaders(List.of("Vary", "Content-Type", "Authorization", "Access-Control-Allow-Credentials", "Access-Control-Allow-Origin"));
        corsConfig.setMaxAge(3600L);

        //
        source.registerCorsConfiguration("/**", corsConfig);
        source.registerCorsConfiguration("/authenticate/**", corsConfig);
        source.registerCorsConfiguration("/oauth2/**", corsConfig);
        return source;
    }

}
