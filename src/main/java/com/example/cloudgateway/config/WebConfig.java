package com.example.cloudgateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            // CORS 를 적용할 URL 패턴을 정의
            .addMapping("/**")
            // 자원 공유를 허락할 Origin 을 지정
            .allowedOrigins("http://localhost:3000")
            // 허용할 HTTP Method 지정
            // "*" 로 모든 Method 허용 가능
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("Authorization", "Content-Type")
            .allowCredentials(true)
            .maxAge(3600L)
        ;
    }
}
