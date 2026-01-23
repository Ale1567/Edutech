package com.example.Cupones.controller;

import com.example.Cupones.models.dto.VersionInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @GetMapping("/")
    public ResponseEntity<VersionInfo> info() {
     
        return ResponseEntity.ok(new VersionInfo("Microservicio Cupones", "1.0.0"));
    }
}