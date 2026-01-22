package com.example.gestionRecursos.models.request;

import lombok.Data;

@Data

public class RecursoRequest {
    private String nombre;
    private Double capacidadTotal;
    private Double capacidadEnUso;
    private String unidadMedida;
}
