package com.example.gestionRespaldos.models.entities;

import org.springframework.hateoas.EntityModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "copiaSeguridad")
public class Copia extends EntityModel<Copia> {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int idCopia;

    @Column(nullable = false)
    private Double version;

    @Column(nullable = false)
    private String nombre;
    
}
