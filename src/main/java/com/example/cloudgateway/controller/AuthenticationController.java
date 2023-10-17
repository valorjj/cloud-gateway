package com.example.cloudgateway.controller;

import com.example.cloudgateway.model.AuthenticateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@RestController
@RequestMapping("/authenticate")
@Slf4j
public class AuthenticationController {

    private final String FRONT_SERVER_URL = "http://localhost:3000"

    @GetMapping("/login")
    public ResponseEntity<?> login(
        @AuthenticationPrincipal OidcUser oidcUser,
        @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
        Model model
    ) throws URISyntaxException {
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

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(FRONT_SERVER_URL));

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).headers(headers).build();
    }

}
