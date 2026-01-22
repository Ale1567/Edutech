package com.example.gestionConsultas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestionConsultas.models.entities.Consulta;
import com.example.gestionConsultas.models.request.ConsultaRequest;
import com.example.gestionConsultas.services.ConsultaService;

@RestController
@RequestMapping

public class ConsultaController {
    @Autowired
    private ConsultaService consultaService;

    @GetMapping
    public List<Consulta> listar() {
        return consultaService.listarTodas();
    }

    @PostMapping
    public ResponseEntity<Consulta> crear(@RequestBody ConsultaRequest request) {
        return ResponseEntity.ok(consultaService.crearConsulta(request));
    }

    @PutMapping("/{id}/responder")
    public ResponseEntity<Consulta> responder(@PathVariable Long id, @RequestBody String respuesta) {
        Consulta actualizada = consultaService.responder(id, respuesta);
        return actualizada != null ? ResponseEntity.ok(actualizada) : ResponseEntity.notFound().build();
    }
    
}
