package com.example.gestionRecursos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestionRecursos.models.entities.Recurso;
import com.example.gestionRecursos.models.request.RecursoRequest;
import com.example.gestionRecursos.services.RecursoService;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")

public class RecursoController {
    @Autowired
    private RecursoService recursoService;

    @GetMapping
    public List<Recurso> listar() {
        return recursoService.listarRecursos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recurso> obtenerPorId(@PathVariable Long id) {
        return recursoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public Recurso crear(@RequestBody RecursoRequest request) {
        Recurso nuevoRecurso = new Recurso();
        nuevoRecurso.setNombre(request.getNombre());
        nuevoRecurso.setCapacidadTotal(request.getCapacidadTotal());
        nuevoRecurso.setCapacidadEnUso(request.getCapacidadEnUso());
        nuevoRecurso.setUnidadMedida(request.getUnidadMedida());
        
        return recursoService.guardarRecurso(nuevoRecurso);
    }
}
