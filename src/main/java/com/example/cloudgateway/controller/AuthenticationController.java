package com.example.cloudgateway.controller;

import com.example.cloudgateway.model.AuthenticateResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.cookie.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/authenticate")
@Slf4j
public class AuthenticationController {

    @GetMapping("/api")
    public ResponseEntity<String> apiTest() {
        return ResponseEntity.ok("1");
    }

    @GetMapping("/login")
    public ResponseEntity<AuthenticateResponse> login(
        @AuthenticationPrincipal OidcUser oidcUser,
        @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
        ServerHttpResponse response
    ) {

        AuthenticateResponse authenticateResponse = AuthenticateResponse.builder()
            .userId(oidcUser.getEmail())
            .accessToken(client.getAccessToken().getTokenValue())
            .refreshToken(Objects.requireNonNull(client.getRefreshToken()).getTokenValue())
            .expiresAt(Objects.requireNonNull(client.getAccessToken().getExpiresAt()).getEpochSecond())
            .authorityList(oidcUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList()
            )
            .build();

        log.info("authenticateResponse := {}", authenticateResponse);

        return ResponseEntity.ok(authenticateResponse);
    }


}
