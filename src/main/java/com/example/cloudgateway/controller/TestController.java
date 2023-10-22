package com.example.cloudgateway.controller;

import brave.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/api")
public class TestController {

    @GetMapping("/v1")
    public ResponseEntity<String> thisIsTest() {
        return ResponseEntity.ok("test is successful");
    }

}
