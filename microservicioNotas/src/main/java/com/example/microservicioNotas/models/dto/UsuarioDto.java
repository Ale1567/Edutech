package com.example.microservicioNotas.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDto {
    private int idUsuario;
    private String nombreUsuario;
    private String rol;
}