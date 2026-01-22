package com.example.gestionRecursos.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data

@Table(name = "recursos_sistema" )

public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_recurso;
    
    private String nombre; 
    private Double capacidadTotal;
    private Double capacidadEnUso;
    private String unidadMedida; 
    private String estado;
}
