package com.example.Login.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity

@Table(name = "usuarios")

public class Usuario {
    @Id
    @GeneratedValue
    private Long id;
    
    private String email;
    private String password;
    private String nombre;
}
