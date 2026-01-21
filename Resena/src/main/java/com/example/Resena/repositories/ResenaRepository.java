package com.example.Resena.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Resena.models.entities.Resena;

@Repository

public interface ResenaRepository extends JpaRepository <Resena, Long> {

    List<Resena>findByCursoId(Long cursoId);
    
}
