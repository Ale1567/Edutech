package com.example.Edutech.models.entities;

import org.springframework.hateoas.RepresentationModel;

import com.example.Edutech.models.enums.Rol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "usuario")
public class Usuario extends RepresentationModel<Usuario> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;

    @Column(nullable = false)
    private String nombre_user;

    @Column(nullable = false)
    private String email_user;

    @Column(nullable = false)
    private String estado_user;

    @Enumerated(EnumType.STRING) 
    private Rol rol;
    
}
