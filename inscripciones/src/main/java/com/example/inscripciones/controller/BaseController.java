package com.example.inscripciones.controller;

import com.example.inscripciones.models.dto.VersionInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BaseController {

    @Value("${app.name}")
    private String nombre;

    @Value("${app.version}")
    private String version;

    @GetMapping("/")
    public ResponseEntity<VersionInfo> base() {
        // Al ser un record, el constructor ya existe automáticamente
        return ResponseEntity.ok(new VersionInfo(nombre, version));
    }
}