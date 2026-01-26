package com.example.microservicioNotas.models.dto;


import lombok.Data;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Data
public class NotaUpdateDto {
    
    @Min(1)
    @Max(7)
    private Double valor; // La nueva calificación del 1 al 7
    
    private String observacion;
}