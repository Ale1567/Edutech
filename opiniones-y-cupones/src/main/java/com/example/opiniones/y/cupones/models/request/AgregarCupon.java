package com.example.opiniones.y.cupones.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class AgregarCupon {
    @NotBlank
    private String codigo;
    @NotNull
    private int porcentaje_descuento;
    
}
