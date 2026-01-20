package com.example.inscripciones.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.inscripciones.models.entities.Inscripcion;
import com.example.inscripciones.models.request.AgregarInscripcion;
import com.example.inscripciones.repositories.InscripcionRepository;

@Service
public class InscripcionService {
    @Autowired
    private InscripcionRepository inscripcionRepository;

    public List<Inscripcion> obtenerTodas() {
        return inscripcionRepository.findAll();
    }

    public Inscripcion registrarInscripcion(AgregarInscripcion nuevo) {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId_estudiante(nuevo.getId_estudiante());
        inscripcion.setId_curso(nuevo.getId_curso());
        // El @PrePersist en tu entidad manejará la fecha automáticamente
        return inscripcionRepository.save(inscripcion);
    }

    public String eliminar(int id) {
        if (inscripcionRepository.existsById(id)) {
            inscripcionRepository.deleteById(id);
            return "Inscripción eliminada correctamente.";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscripción no encontrada.");
        }
    }
}