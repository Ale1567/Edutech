package com.example.microservicioNotas.models.dto;

import lombok.Data;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EvaluacionDto {
    private Long id;
    private String titulo;
    private LocalDateTime fechaFin;
}