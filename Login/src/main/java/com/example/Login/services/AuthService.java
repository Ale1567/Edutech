package com.example.Login.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Login.models.entities.Usuario;
import com.example.Login.models.request.LoginRequest;
import com.example.Login.repositories.UsuarioRepository;

@Service

public class AuthService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> validarAcceso(LoginRequest request) {
        return usuarioRepository.findByEmail(request.getEmail())
                .filter(u -> u.getPassword().equals(request.getPassword()));
    }
    
}
