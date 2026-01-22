package com.example.gestionRecursos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestionRecursos.models.entities.Recurso;
import com.example.gestionRecursos.repositories.RecursoRepository;

@Service
public class RecursoService {

    @Autowired
    private RecursoRepository recursoRepository;
    public List<Recurso> listarRecursos() {
        return recursoRepository.findAll();
    }
    public Recurso guardarRecurso(Recurso recurso) {
        // Lógica simple para determinar el estado según el uso
        double porcentajeUso = (recurso.getCapacidadEnUso() / recurso.getCapacidadTotal()) * 100;
        
        if (porcentajeUso > 90) {
            recurso.setEstado("CRITICO");
        } else if (porcentajeUso > 70) {
            recurso.setEstado("ADVERTENCIA");
        } else {
            recurso.setEstado("OPTIMIZADO");
        }
        
        return recursoRepository.save(recurso);
    }

    public Optional<Recurso> obtenerPorId(Long id) {
        return recursoRepository.findById(id);
    }
}
