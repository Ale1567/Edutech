package com.example.Resena.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Resena.models.entities.Resena;
import com.example.Resena.repositories.ResenaRepository;

@Service

public class ResenaService {
    @Autowired
    private ResenaRepository resenaRepository;

    public Resena crearResena(Resena resena) {
        return resenaRepository.save(resena);
    }
    public List<Resena> obtenerResenasPorCurso(Long cursoId) {
        return resenaRepository.findByCursoId(cursoId);
    }
    public List<Resena> listarTodas() {
        return resenaRepository.findAll();
    }
}
