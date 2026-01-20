package com.example.opiniones.y.cupones.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "opiniones")
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_opinion;

    @Column(nullable = false)
    private int id_curso; 

    @Column(nullable = false)
    private int id_usuario;

    @Column(nullable = false, length = 500)
    private String comentario;

    @Column(nullable = false)
    private int calificacion;

    @Column(nullable = false)
    private LocalDateTime fecha;
}