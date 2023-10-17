package com.example.cloudgateway.controller;

import com.example.cloudgateway.model.AuthenticateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/authenticate")
@Slf4j
public class AuthenticationController {

    @GetMapping("/login")
    public ResponseEntity<AuthenticateResponse> login(
        @AuthenticationPrincipal OidcUser oidcUser,
        @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
        Model model
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

        return ResponseEntity.ok(authenticateResponse);
    }

}
