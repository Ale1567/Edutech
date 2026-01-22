package com.example.gestionConsultas.models.request;

import lombok.Data;

@Data

public class ConsultaRequest {
    private Long estudianteId;
    private String titulo;
    private String mensaje;
    
}
