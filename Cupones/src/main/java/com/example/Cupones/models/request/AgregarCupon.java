package com.example.Cupones.models.request;

import lombok.Data;

@Data

public class AgregarCupon {
    
    private String codigo;
    private Integer porcentajeDescuento;
}
