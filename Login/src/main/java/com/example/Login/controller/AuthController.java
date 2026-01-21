package com.example.Login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Login.models.request.LoginRequest;
import com.example.Login.services.AuthService;

@RestController
@RequestMapping

public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authService.validarAcceso(request)
                .map(u -> ResponseEntity.ok("Bienvenido " + u.getNombre()))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o clave incorrectos"));
    }
}
