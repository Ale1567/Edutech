package com.example.Resena.models.request;

import lombok.Data;

@Data

public class AgregarOpinion {
    private Long cursoId;
    private String nombreUsuario;
    private String comentario;
    private Integer calificacion;
    
}
