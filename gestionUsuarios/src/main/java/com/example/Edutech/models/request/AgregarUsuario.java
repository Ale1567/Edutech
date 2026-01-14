package com.example.Edutech.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AgregarUsuario {
    @NotBlank
    private String nombre_user;
    @NotBlank 
    private String email_user;
    @NotBlank
    private String estado_user;
}
