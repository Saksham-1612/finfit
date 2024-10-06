package com.finfit.finfit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/")
    public ResponseEntity<?> healthCheck()
    {
        return new ResponseEntity<>("Up and Running",HttpStatus.OK);
    }
}
