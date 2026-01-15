package com.example.Edutech.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.Edutech.models.entities.Usuario;
import com.example.Edutech.models.request.ActualizarUsuario;
import com.example.Edutech.models.request.AgregarUsuario;
import com.example.Edutech.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(int usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        return usuario;
    }

    public Usuario agregarUsuario(AgregarUsuario nuevo){
        Usuario usuario = new Usuario();
        usuario.setNombre_user(nuevo.getNombre_user());
        usuario.setEmail_user(nuevo.getEmail_user());
        usuario.setEstado_user(nuevo.getEstado_user());
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(ActualizarUsuario nuevo){
        Usuario usuario = usuarioRepository.findById(nuevo.getId_usuario()).orElse(null);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        usuario.setNombre_user(nuevo.getNombre_user());
        usuario.setEmail_user(nuevo.getEmail_user());
        usuario.setEstado_user(nuevo.getEstado_user());
        return usuarioRepository.save(usuario);
    }

    public String eliminarUsuario(int usuarioId){
        if (usuarioRepository.existsById(usuarioId)) {
            usuarioRepository.deleteById(usuarioId);
            return "Usuario eliminado";
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
    }

    
}
