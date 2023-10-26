package com.example.cloudgateway.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

import java.util.Collection;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AuthenticateResponse(
    String userId,
    String accessToken,
    String refreshToken,
    Long expiresAt,
    Collection<String> authorityList
) {
}
