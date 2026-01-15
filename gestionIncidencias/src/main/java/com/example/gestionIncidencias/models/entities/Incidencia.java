package com.example.gestionIncidencias.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "incidencia")
public class Incidencia {
    private int id_incidencias;
    private String descripcion;
    private String estado;
    private int prioridad;
}
