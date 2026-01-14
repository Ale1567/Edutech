package com.example.gestionRoles.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActualizarRol {
    
    @NotBlank
    private int id_rol;
    @NotBlank
    private String nombre_rol;
}
