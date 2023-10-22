package com.example.cloudgateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Slf4j
public class IndexController {

    @GetMapping
    public ResponseEntity<String> thisIsAlsoTest(){
        return ResponseEntity.ok("this is successful");
    }
}
