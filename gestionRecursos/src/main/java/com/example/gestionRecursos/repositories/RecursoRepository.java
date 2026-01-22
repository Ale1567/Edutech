package com.example.gestionRecursos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestionRecursos.models.entities.Recurso;

@Repository

public interface RecursoRepository extends JpaRepository<Recurso, Long> {
    
}
