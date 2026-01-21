package com.example.gestionNotificaciones.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActualizarNotif {
    @NotBlank
    private int id_notif;

    @NotBlank
    private String titulo;
    
    @NotBlank
    private String descripcion;
}
