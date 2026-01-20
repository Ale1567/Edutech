package com.example.opiniones.y.cupones.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgregarOpinion {
    @NotNull
    private int id_curso;

    @NotNull
    private int id_usuario;

    @NotBlank
    private String comentario;

    @NotNull
    private int calificacion;
}