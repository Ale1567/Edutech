package com.example.Edutech.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Edutech.models.dto.VersionInfo;

@RestController
@RequestMapping("/")
public class BaseController {

    @Value("${app.name}")
    private String nombre;

    @Value("${app.version}")
    private String version;

    @GetMapping("")
    public VersionInfo base() {
        return new VersionInfo(nombre,version);
    }
    
}
