package com.example.gestionConsultas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestionConsultas.models.entities.Consulta;
import com.example.gestionConsultas.models.request.ConsultaRequest;
import com.example.gestionConsultas.repositories.ConsultaRepository;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;

    public Consulta crearConsulta(ConsultaRequest request) {
        Consulta consulta = new Consulta();
        consulta.setEstudianteId(request.getEstudianteId());
        consulta.setTitulo(request.getTitulo());
        consulta.setMensaje(request.getMensaje());
        return consultaRepository.save(consulta);
    }
    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }

    public Optional<Consulta> obtenerPorId(Long id) {
        return consultaRepository.findById(id);
    }

    public Consulta responder(Long id, String respuesta) {
        Optional<Consulta> opt = consultaRepository.findById(id);
        if (opt.isPresent()) {
            Consulta c = opt.get();
            c.setRespuesta(respuesta);
            c.setEstado("RESPONDIDA");
            return consultaRepository.save(c);
        }
        return null;
    }
}
