package com.example.gestionRoles.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AgregarRol {
    
    @NotBlank
    private String nombre_rol;
}
