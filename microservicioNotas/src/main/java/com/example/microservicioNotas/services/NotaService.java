package com.example.microservicioNotas.services;


import com.example.microservicioNotas.models.dto.*;
import com.example.microservicioNotas.models.entities.Nota;
import com.example.microservicioNotas.repositories.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotaService {

    @Autowired
    private NotaRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private final String URL_USUARIOS = "http://localhost:8080/api/usuarios/"; 
    private final String URL_EVALUACIONES = "http://localhost:8090/api/evaluaciones/";

    public Nota guardarNota(NotaRequest request) {

        if (repository.existsByAlumnoIdAndEvaluacionId(request.getAlumnoId(), request.getEvaluacionId())) {
            throw new RuntimeException("El alumno ya tiene nota en esta evaluación");
        }


        UsuarioDto alumno;
        try {
            alumno = restTemplate.getForObject(URL_USUARIOS + request.getAlumnoId(), UsuarioDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Alumno no encontrado en el sistema");
        }
        
        if (alumno != null && !"ALUMNO".equalsIgnoreCase(alumno.getRol())) {
             throw new RuntimeException("El usuario no tiene el rol de ALUMNO");
        }

        EvaluacionDto evaluacion;
        try {
            evaluacion = restTemplate.getForObject(URL_EVALUACIONES + request.getEvaluacionId(), EvaluacionDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Evaluación no encontrada");
        }


        if (LocalDateTime.now().isAfter(evaluacion.getFechaFin())) {
            throw new RuntimeException("La evaluación ya cerró");
        }

        Nota nota = new Nota();
        nota.setAlumnoId(request.getAlumnoId());
        nota.setEvaluacionId(request.getEvaluacionId());
        nota.setCalificacion(request.getValor());
        nota.setObservacion(request.getObservacion());

        return repository.save(nota);
    }

    public List<Nota> listarPorAlumno(Integer alumnoId) {
        return repository.findByAlumnoId(alumnoId);
    }
}