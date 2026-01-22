package com.example.gestionRespaldos.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AgregarCopia {
    
    @NotBlank
    private Double version;

    @NotBlank
    private String nombre;
}
