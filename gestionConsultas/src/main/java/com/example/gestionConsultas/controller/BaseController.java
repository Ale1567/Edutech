package com.example.gestionConsultas.controller;

import com.example.gestionConsultas.models.dto.VersionInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @Value("${app.nombre}")
    private String nombreApp;

    @Value("${app.version}")
    private String versionApp;

    @GetMapping("/info")
    public ResponseEntity<VersionInfo> info() {
        // Retorna el DTO con los datos inyectados dinámicamente
        return ResponseEntity.ok(new VersionInfo(nombreApp, versionApp));
    }
}