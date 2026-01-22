package com.example.Edutech.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActualizarUsuario {

    @NotBlank
    private int idUsuario;
    @NotBlank
    private String nombre;
    @NotBlank 
    private String email;
    @NotBlank
    private String estado;

}
