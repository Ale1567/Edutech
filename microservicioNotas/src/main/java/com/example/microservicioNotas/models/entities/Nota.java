package com.example.microservicioNotas.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Table(name = "notas")
@Data
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNota;

    @Column(nullable = false)
    private Integer alumnoId;

    @Column(nullable = false)
    private Long evaluacionId;

    @Column(nullable = false)
    @Min(1)
    @Max(7)
    private Double calificacion;

    private String observacion;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
    }
}