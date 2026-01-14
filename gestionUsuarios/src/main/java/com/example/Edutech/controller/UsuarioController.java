package com.example.Edutech.controller;

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

import com.example.Edutech.models.entities.Usuario;
import com.example.Edutech.models.request.ActualizarUsuario;
import com.example.Edutech.models.request.AgregarUsuario;
import com.example.Edutech.services.UsuarioService;

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
    public Usuario obtenerPorId(@PathVariable int idUsuario) {
        return usuarioService.obtenerUsuarioPorId(idUsuario); 
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
        return usuarioService.eliminarUsurio(idUsuario);
    }
}
