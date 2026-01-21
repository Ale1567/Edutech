package com.example.gestionRespaldos.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActualizarCopia {
    
    @NotBlank
    private int id_copia;

    @NotBlank
    private int version;

    @NotBlank
    private String nombre;
}

