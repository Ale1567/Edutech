package com.example.gestionProveedores.controller;

import com.example.gestionProveedores.models.dto.VersionInfo;
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
       
        return ResponseEntity.ok(new VersionInfo(nombreApp, versionApp));
    }
}