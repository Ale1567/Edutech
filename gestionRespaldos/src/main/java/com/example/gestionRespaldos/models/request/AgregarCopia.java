package com.example.gestionRespaldos.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AgregarCopia {
    
    @NotBlank
    private int version;

    @NotBlank
    private String nombre;
}
