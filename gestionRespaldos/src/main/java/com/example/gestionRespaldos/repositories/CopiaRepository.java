package com.example.gestionRespaldos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestionRespaldos.models.entities.Copia;

@Repository
public interface CopiaRepository extends JpaRepository<Copia, Integer>{
    
}
