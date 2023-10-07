package com.example.cloudgateway.model;

import lombok.Builder;

import java.util.Collection;

@Builder
public record AuthenticateResponse(
    String userId,
    String accessToken,
    String refreshToken,
    Long expiresAt,
    Collection<String> authorityList
) {
}
