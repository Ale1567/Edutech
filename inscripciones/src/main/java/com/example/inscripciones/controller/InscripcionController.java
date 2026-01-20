package com.example.inscripciones.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.inscripciones.models.entities.Inscripcion;
import com.example.inscripciones.models.request.AgregarInscripcion;
import com.example.inscripciones.services.InscripcionService;

@RestController
@RequestMapping("inscripcion")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @GetMapping("")
    public List<Inscripcion> obtenerTodo() {
        return inscripcionService.obtenerTodas();
    }

    @PostMapping("")
    public Inscripcion crear(@RequestBody AgregarInscripcion nueva) {
        return inscripcionService.registrarInscripcion(nueva);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        return inscripcionService.eliminar(id);
    }
}