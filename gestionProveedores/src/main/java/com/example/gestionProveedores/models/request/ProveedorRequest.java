package com.example.gestionProveedores.models.request;

import lombok.Data;

@Data
public class ProveedorRequest {
    private String nombreEmpresa;
    private String ruc;
    private String categoria;
    private String contactoEmail;
}
