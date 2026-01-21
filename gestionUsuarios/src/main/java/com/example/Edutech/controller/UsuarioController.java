package com.example.Edutech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Edutech.models.entities.Usuario;
import com.example.Edutech.models.request.ActualizarUsuario;
import com.example.Edutech.models.request.AgregarUsuario;
import com.example.Edutech.services.UsuarioService;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RequestMapping("usuario")
@RestController
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("")
    public List<Usuario> obtenerTodo(){
        return usuarioService.obtenerUsuarios();
    }

    @GetMapping("/{usuarioid}")
    public EntityModel<Usuario> obtenerPorId(@PathVariable int idUsuario){
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);

        Link deleteLink = linkTo(UsuarioController.class).slash(idUsuario).withRel("Eliminar Marca");
        Link selfLink = linkTo(methodOn(UsuarioController.class).obtenerTodo()).withRel("Obtener todas las marcas");
   
        return EntityModel.of(usuario,selfLink,deleteLink);
    }   

       
    
    

    @PostMapping("")
    public Usuario agregarUsuario(@RequestBody AgregarUsuario nueva) {            
        return usuarioService.agregarUsuario(nueva);
    }
    

    @PutMapping("")
    public Usuario actualizarUsuario(@RequestBody ActualizarUsuario nueva ) {
        return usuarioService.actualizarUsuario(nueva);
    }

    @DeleteMapping("/{idUsuario}")
    public String eliminarUsuario(@PathVariable int idUsuario){
        return usuarioService.eliminarUsuario(idUsuario);
    }
}
