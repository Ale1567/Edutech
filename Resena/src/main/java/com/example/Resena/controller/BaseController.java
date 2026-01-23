package com.example.Resena.controller;

import com.example.Resena.models.dto.VersionInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    @GetMapping("/info") // O la ruta "/" si prefieres
    public ResponseEntity<VersionInfo> info() {
        return ResponseEntity.ok(new VersionInfo("Microservicio [PONER NOMBRE AQUI]", "1.0.0"));
    }
}