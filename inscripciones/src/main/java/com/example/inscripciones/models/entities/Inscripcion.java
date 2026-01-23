package com.example.inscripciones.models.entities;

import java.time.LocalDateTime;
import com.example.inscripciones.models.enums.EstadoInscripcion;
import lombok.Data;

import jakarta.persistence.*; 

@Entity
@Data
@Table(name = "inscripcion")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idInscripcion;

    // ID del estudiante (Microservicio Usuarios)
    @Column(nullable = false)
    private int idEstudiante;

    // ID del curso (Microservicio Cursos)
    @Column(nullable = false)
    private Long idCurso;

    // --- NUEVOS CAMPOS DE SEGUIMIENTO ---

    // 1. El porcentaje de avance (0.0 a 100.0)
    // Le ponemos 0.0 por defecto para que no sea null al crear
    @Column(name = "progreso_porcentaje")
    private Double progreso = 0.0;

    // 2. Estado del curso (Usamos un Enum para evitar errores de escritura)
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EstadoInscripcion estado = EstadoInscripcion.CURSANDO;

    // 3. Última vez que el alumno entró al curso (Ideal para "Continuar donde dejaste")
    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;

    // 4. Fecha en que completó el 100%
    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    // --- CAMPOS ORIGINALES ---

    @Column(name = "fecha_inscripcion")
    private LocalDateTime fechaInscripcion;

    @PrePersist 
    protected void onCreate() {
        this.fechaInscripcion = LocalDateTime.now();
        // Al inscribirse, el último acceso es "ahora"
        this.ultimoAcceso = LocalDateTime.now(); 
    }
}

    

