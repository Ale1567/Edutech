package com.example.gestionRoles.controller;

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

import com.example.gestionRoles.models.entities.Rol;
import com.example.gestionRoles.models.request.ActualizarRol;
import com.example.gestionRoles.models.request.AgregarRol;
import com.example.gestionRoles.services.RolServices;

@RequestMapping("rol")
@RestController
public class RolController {
    
    @Autowired
    private RolServices rolServices;

    @GetMapping("")
    public List<Rol> obtenerTodo(){
        return rolServices.obtenerRoles();
    }

     @GetMapping("/{rolid}")
    public Rol obtenerPorId(@PathVariable int idRol) {
        return rolServices.obtenerRolPorId(idRol); 
    }

    @PostMapping("")
    public Rol agregarRol(@RequestBody AgregarRol nueva) {            
        return rolServices.agregarRol(nueva);
    }
    

    @PutMapping("")
    public Rol actualizarRol(@RequestBody ActualizarRol nueva ) {
        return rolServices.actualizarRol(nueva);
    }

    @DeleteMapping("/{idRol}")
    public String eliminarRol(@PathVariable int idRol){
        return rolServices.eliminarRol(idRol);
    }
}
