package com.example.Edutech.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AgregarUsuario {
    @NotBlank
    private String nombre;
    @NotBlank 
    private String email;
    @NotBlank
    private String estado;
}
