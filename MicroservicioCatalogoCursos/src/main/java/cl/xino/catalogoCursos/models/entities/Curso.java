package cl.xino.catalogoCursos.models.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.GenerationType;

@Entity
@Data
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCurso;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 1000)
    private String descripcion;

    private String categoria;

    @Column (nullable = false)
    private int precio;

    @Column
    private int instructorId;


    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

       
}
