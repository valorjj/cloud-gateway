package com.example.cloudgateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.*;

@Configuration
@EnableWebFluxSecurity
public class OktaConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(auth -> auth
            // .pathMatchers("/authenticate/api").permitAll()
            .anyExchange().permitAll());
        http.oauth2Login(withDefaults());
        http.oauth2ResourceServer(auth -> auth.jwt(withDefaults()));
        // add CORS configuration
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();

        // Credentials 옵션을 사용하기 위해서는 와일드카드(*) 사용 불가능

        corsConfig.setAllowedOrigins(List.of("*"));
        // Preflight 요청 -> OPTIONS
        corsConfig.setAllowedMethods(List.of("GET", "POST", "OPTIONS"));
        corsConfig.addAllowedHeader("*");
        // Credentials 옵션을 사용해야 인증과 관련된 정보(-> 쿠키)를 전송할 수 있다.
        // corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

}
