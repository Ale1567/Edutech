package com.example.gestionIncidencias.models.entities;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "incidencia")
public class Incidencia extends RepresentationModel<Incidencia>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_incidencias;
    
    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private int prioridad;
}
