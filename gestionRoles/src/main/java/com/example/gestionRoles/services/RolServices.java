package com.example.gestionRoles.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestionRoles.models.entities.Rol;
import com.example.gestionRoles.models.request.ActualizarRol;
import com.example.gestionRoles.models.request.AgregarRol;
import com.example.gestionRoles.repositories.RolRepository;

@Service
public class RolServices {
    
    @Autowired
    private RolRepository rolRepository;

    public List<Rol> obtenerRoles(){
        return rolRepository.findAll();
    }

    public Rol obtenerRolPorId(int rolId){
        Rol rol = rolRepository.findById(rolId).orElse(null);
        if (rol == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado");
        }
        return rol;
    }

    public Rol agregarRol(AgregarRol nuevo){
        Rol rol = new Rol();
        rol.setNombre_rol(nuevo.getNombre_rol());
        return rolRepository.save(rol);
    }

    public Rol actualizarRol(ActualizarRol nuevo){
        Rol rol = rolRepository.findById(nuevo.getId_rol()).orElse(null);
        if (rol == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado");
        }
        rol.setNombre_rol(nuevo.getNombre_rol());
        return rolRepository.save(rol);
    }

    public String eliminarRol(int rolId){
        if (rolRepository.existsById(rolId)) {
            rolRepository.deleteById(rolId);
            return "Rol eliminado";
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado");
        }
    }

    
}
