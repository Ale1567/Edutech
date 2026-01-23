package com.example.inscripciones.models.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InscripcionDetalleDto {
    private Long idInscripcion;
    private String nombreCurso;  
    private Double progreso;      
    private String estado;        
    private LocalDateTime ultimoAcceso;
}