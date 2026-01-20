package com.example.inscripciones.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgregarInscripcion {

    @NotNull(message = "El id estudiante es obligatorio")
    private Integer id_estudiante;
    
    @NotNull (message = "El id cursos es obligatorio")
    private Integer id_curso; 
    
}
