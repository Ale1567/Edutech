package com.example.gestionNotificaciones.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestionNotificaciones.models.entities.Notificacion;
import com.example.gestionNotificaciones.models.request.ActualizarNotif;
import com.example.gestionNotificaciones.models.request.AgregarNotif;
import com.example.gestionNotificaciones.repositories.NotificacionRepository;

@Service
public class NotificacionService {
    @Autowired
    private NotificacionRepository notificacionRepository;

    public List<Notificacion> obtenerTodasLasNotificaciones(){
        return notificacionRepository.findAll();
    }


    public Notificacion obtenerNotificacionPorId(int notifId){
        Notificacion notificacion = notificacionRepository.findById(notifId).orElse(null);
        if (notificacion == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Notificacion no encontrada.");
        }
        return notificacion;
    }

    public Notificacion agregarNotificacion(AgregarNotif nuevo){
        Notificacion notificacion = new Notificacion();
        notificacion.setTitulo(nuevo.getTitulo());
        notificacion.setDescripcion(nuevo.getDescripcion());
        return notificacionRepository.save(notificacion);
    }

    public Notificacion actualizarNotificacion(ActualizarNotif nuevo){
        Notificacion notificacion = notificacionRepository.findById(nuevo.getId_notif()).orElse(null);
        if (notificacion == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificacion no encontrada.");
        }else{
            notificacion.setTitulo(nuevo.getTitulo());
            notificacion.setDescripcion(nuevo.getDescripcion());
            return notificacionRepository.save(notificacion);
        }
    }


    public String eliminarNotificacion(int notifId){
        if (notificacionRepository.existsById(notifId)) {
            notificacionRepository.deleteById(notifId);
            return "Notificacion eliminda correctamente.";
        }else{
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificacion no encontrada.");
        }
    }

}
