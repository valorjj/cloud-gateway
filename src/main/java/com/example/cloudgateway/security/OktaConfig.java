package com.example.cloudgateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.*;

@Configuration
@EnableWebFluxSecurity
public class OktaConfig {

    // 이슈1.
    // api 로 인증 없이 접근할 수 있도록 설정 변경하기

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(auth -> auth
            .pathMatchers("/authenticate/api").permitAll()
            .pathMatchers("/api/v1").permitAll()
            .pathMatchers("/**").permitAll()
            .anyExchange().permitAll());
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.cors(ServerHttpSecurity.CorsSpec::disable);
        http.oauth2Login(withDefaults());
        http.oauth2ResourceServer(auth -> auth.jwt(withDefaults()));
        return http.build();
    }


}
