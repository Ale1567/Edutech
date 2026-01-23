package com.example.gestionConsultas.controller;

import com.example.gestionConsultas.models.dto.VersionInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BaseController {
    @GetMapping("/info")
    public ResponseEntity<VersionInfo> info() {
        return ResponseEntity.ok(new VersionInfo());
    }
}
