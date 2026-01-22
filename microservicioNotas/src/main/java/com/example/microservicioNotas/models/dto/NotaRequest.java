package com.example.microservicioNotas.models.dto;

import lombok.Data;

@Data
public class NotaRequest {
    private Integer alumnoId;
    private Long evaluacionId;
    private Double valor;
    private String observacion;
}