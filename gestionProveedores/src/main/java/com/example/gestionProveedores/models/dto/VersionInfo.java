package com.example.gestionProveedores.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VersionInfo {
    private String name;
    private String version;
}