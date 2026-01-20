package com.example.inscripciones.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.inscripciones.models.entities.Inscripcion;

@Repository

public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {
    
}
