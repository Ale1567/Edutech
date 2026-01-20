package com.example.opiniones.y.cupones.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cupones")

public class Cupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cupon;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private int porcentaje_descuento;
    
    @Column(nullable =  false)
    private boolean activo;

    
}
