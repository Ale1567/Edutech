package com.example.gestionReportes.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActualizarReporte {

    @NotBlank
    private int idReporte;
    
    @NotBlank
    private String tipoReporte;
    
    @NotBlank
    private String descripcion;
    
    @NotBlank
    private String fechaGeneracion;
    
    @NotBlank
    private String estado;
}
