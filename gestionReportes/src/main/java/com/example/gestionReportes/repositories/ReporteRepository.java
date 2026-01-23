package com.example.gestionReportes.repositories;

import org.springframework.stereotype.Repository;

import com.example.gestionReportes.models.entities.Reporte;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {
    
}
