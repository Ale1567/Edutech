package com.example.Resena.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Resena.models.entities.Resena;
import com.example.Resena.services.ResenaService;

@RestController
@RequestMapping

public class ResenaController {
    @Autowired
    private ResenaService resenaService;

    @PostMapping
    public ResponseEntity<Resena> guardar(@RequestBody Resena resena) {
        return ResponseEntity.ok(resenaService.crearResena(resena));
    }
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<Resena>> listarPorCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(resenaService.obtenerResenasPorCurso(cursoId));
    }
}   
