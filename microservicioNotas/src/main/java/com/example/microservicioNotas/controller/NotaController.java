package com.example.microservicioNotas.controller;


import com.example.microservicioNotas.models.dto.NotaRequest;
import com.example.microservicioNotas.models.entities.Nota;
import com.example.microservicioNotas.services.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
public class NotaController {

    @Autowired
    private NotaService service;

    @PostMapping
    public ResponseEntity<?> crearNota(@RequestBody NotaRequest request) {
        try {
            Nota nota = service.guardarNota(request);
            return ResponseEntity.ok(nota);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/alumno/{id}")
    public ResponseEntity<List<Nota>> listarPorAlumno(@PathVariable Integer id) {
        return ResponseEntity.ok(service.listarPorAlumno(id));
    }
}