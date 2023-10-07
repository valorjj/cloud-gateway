package com.example.cloudgateway.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(
    String errorMessage,
    int errorCode

) {
}
