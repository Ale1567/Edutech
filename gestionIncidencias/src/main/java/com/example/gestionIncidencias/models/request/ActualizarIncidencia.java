package com.example.gestionIncidencias.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActualizarIncidencia {
    
    @NotBlank
    private int idIncidencia;
    @NotBlank
    private String descripcion;
    @NotBlank 
    private String estado;
    @NotBlank
    private String prioridad;

}
