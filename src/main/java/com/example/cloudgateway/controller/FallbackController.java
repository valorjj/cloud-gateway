package com.example.cloudgateway.controller;

import com.example.cloudgateway.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/orderServiceFallback")
    public ResponseEntity<ErrorResponse> orderServiceFallback() {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.builder()
                .errorMessage("order-service 와의 통신에 실패했습니다.")
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build()
            );
    }

    @GetMapping("/productServiceFallback")
    public ResponseEntity<ErrorResponse> productServiceFallback() {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.builder()
                .errorMessage("product-service 와의 통신에 실패했습니다.")
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build()
            );
    }
    @GetMapping("/paymentServiceFallback")
    public ResponseEntity<ErrorResponse> paymentServiceFallback() {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.builder()
                .errorMessage("payment-service 와의 통신에 실패했습니다.")
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build()
            );
    }


}
