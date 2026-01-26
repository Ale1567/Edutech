package com.example.microservicioNotas.services;

import com.example.microservicioNotas.models.dto.*;
import com.example.microservicioNotas.models.entities.Nota;
import com.example.microservicioNotas.repositories.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; // Importante
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

    // Inyectamos las URLs desde el properties
    @Value("${microservicio.usuarios.url}")
    private String urlUsuariosBase;

    @Value("${microservicio.evaluaciones.url}")
    private String urlEvaluacionesBase;

    public Nota guardarNota(NotaRequest request) {

        if (repository.existsByAlumnoIdAndEvaluacionId(request.getAlumnoId(), request.getEvaluacionId())) {
            throw new RuntimeException("El alumno ya tiene nota en esta evaluación");
        }

        UsuarioDto alumno;
        try {
            // Ajustamos la ruta para que use la URL de la nube
            String urlUsuarios = urlUsuariosBase + "/api/usuarios/" + request.getAlumnoId();
            alumno = restTemplate.getForObject(urlUsuarios, UsuarioDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Alumno no encontrado en el sistema");
        }
        
        if (alumno != null && !"ALUMNO".equalsIgnoreCase(alumno.getRol())) {
             throw new RuntimeException("El usuario no tiene el rol de ALUMNO");
        }

        EvaluacionDto evaluacion;
        try {
            // Ajustamos la ruta para que use la URL de evaluaciones (nuestro otro micro)
            String urlEvas = urlEvaluacionesBase + "/api/evaluaciones/" + request.getEvaluacionId();
            evaluacion = restTemplate.getForObject(urlEvas, EvaluacionDto.class);
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

    public Nota actualizarNota(Long id, Double nuevoValor, String nuevaObservacion) {
    // 1. Buscar la nota usando tu nombre de ID (idNota)
    Nota notaExistente = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("La nota con ID " + id + " no existe"));

    // 2. Validar que la evaluación aún permite cambios
    EvaluacionDto evaluacion;
    try {
        String urlEvas = urlEvaluacionesBase + "/api/evaluaciones/" + notaExistente.getEvaluacionId();
        evaluacion = restTemplate.getForObject(urlEvas, EvaluacionDto.class);
    } catch (Exception e) {
        throw new RuntimeException("Error: No se pudo verificar la evaluación en la nube.");
    }

    // Usamos el fechaFin de tu DTO
    if (evaluacion != null && LocalDateTime.now().isAfter(evaluacion.getFechaFin())) {
        throw new RuntimeException("Acceso denegado: La evaluación '" + evaluacion.getTitulo() + "' ya cerró.");
    }

    // 3. Actualizar con tus setters
    notaExistente.setCalificacion(nuevoValor);
    notaExistente.setObservacion(nuevaObservacion);

    return repository.save(notaExistente);
}

}

