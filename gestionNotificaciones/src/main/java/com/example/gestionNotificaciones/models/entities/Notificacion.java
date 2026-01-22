package com.example.gestionNotificaciones.models.entities;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "notificacion")
public class Notificacion extends RepresentationModel<Notificacion>{
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idNotif;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descripcion;
}

    

