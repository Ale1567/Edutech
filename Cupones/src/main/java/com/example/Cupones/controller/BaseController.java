package com.example.Cupones.controller;

import com.example.Cupones.models.dto.VersionInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    // Extrae el valor de app.nombre del application.properties
    @Value("${app.nombre}")
    private String nombreApp;

    // Extrae el valor de app.version del application.properties
    @Value("${app.version}")
    private String versionApp;

    @GetMapping("/")
    public ResponseEntity<VersionInfo> info() {
        // Usamos las variables inyectadas para crear el DTO
        return ResponseEntity.ok(new VersionInfo(nombreApp, versionApp));
    }
}