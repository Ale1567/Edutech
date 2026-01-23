package com.example.gestionNotificaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestionNotificaciones.models.entities.Notificacion;
import com.example.gestionNotificaciones.models.request.ActualizarNotif;
import com.example.gestionNotificaciones.models.request.AgregarNotif;
import com.example.gestionNotificaciones.services.NotificacionService;


@RequestMapping("notificacion")
@RestController
public class NotificacionController {
    
    @Autowired
    private NotificacionService notificacionService;

    @GetMapping("")
    public List<Notificacion> obtenerTodo() {
        return notificacionService.obtenerTodasLasNotificaciones();
    }

    @GetMapping("/{idNotif}")
    public Notificacion obtenerPorId(@PathVariable int idNotif) {
        return notificacionService.obtenerNotificacionPorId(idNotif); 
    }
        

    @PostMapping("")
    public Notificacion agregarNotificacion(@RequestBody AgregarNotif nueva) {            
        return notificacionService.agregarNotificacion(nueva);
    }
    

    @PutMapping("")
    public Notificacion actualizarNotificacion(@RequestBody ActualizarNotif nueva ) {
        return notificacionService.actualizarNotificacion(nueva);
    }

    @DeleteMapping("/{idMarca}")
    public String eliminarNotificacion(@PathVariable int idMarca){
        return notificacionService.eliminarNotificacion(idMarca);
    }
}
