package com.example.inscripciones.models.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table (name = "inscripcion")

public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_inscripcion;

    // solicita la id de microservio usuario
    @Column(nullable = false)
    private int id_estudiante;

    // solicita la id del microservio curso
    @Column(nullable = false)
    private int id_curso;

    // columna donde se guarda la fecha
    @Column(name = "fecha_inscripcion")
    private LocalDateTime fechaInscripcion;
   
    @PrePersist  // permite envia de forma automatica la fecha 
    protected void onCreate() {
        this.fechaInscripcion = LocalDateTime.now();
    }
}

    

