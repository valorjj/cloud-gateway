package com.example.cloudgateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/testing")
public class TestController {

    @GetMapping
    public ResponseEntity<String> testing() {
        return ResponseEntity.ok("hi");
    }

}
