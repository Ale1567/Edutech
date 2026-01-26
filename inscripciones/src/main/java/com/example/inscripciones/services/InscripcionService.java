package com.example.inscripciones.services;

import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.example.inscripciones.models.dto.CursoDto;
import com.example.inscripciones.models.dto.InscripcionDetalleDto;
import com.example.inscripciones.models.enums.EstadoInscripcion;
import com.example.inscripciones.models.entities.Inscripcion;
import com.example.inscripciones.models.request.AgregarInscripcion;
import com.example.inscripciones.repositories.InscripcionRepository;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${microservicio.cursos.url}")
    private String urlCursos;

    @Value("${microservicio.usuarios.url}")
    private String urlUsuarios;


    public List<InscripcionDetalleDto> obtenerCursosDeEstudiante(int idEstudiante) {
        List<Inscripcion> inscripciones = inscripcionRepository.findByIdEstudiante(idEstudiante);

        return inscripciones.stream().map(inscripcion -> {
            InscripcionDetalleDto dto = new InscripcionDetalleDto();
            
            dto.setIdInscripcion((long) inscripcion.getIdInscripcion());
            dto.setProgreso(inscripcion.getProgreso());
            dto.setEstado(inscripcion.getEstado().toString());
            dto.setUltimoAcceso(inscripcion.getUltimoAcceso());

            try {
                String finalUrlCursos = urlCursos.endsWith("/") ? urlCursos : urlCursos + "/";
                CursoDto curso = restTemplate.getForObject(finalUrlCursos + inscripcion.getIdCurso(), CursoDto.class);
                if (curso != null) {
                    dto.setNombreCurso(curso.getNombre());
                }
            } catch (Exception e) {
                dto.setNombreCurso("Curso ID: " + inscripcion.getIdCurso() + " (Info no disponible)");
            }
            
            return dto;
        }).collect(Collectors.toList());
    }


    public Inscripcion registrarInscripcion(AgregarInscripcion nuevo) {
        

        try {
            String finalUrlUsuarios = urlUsuarios.endsWith("/") ? urlUsuarios : urlUsuarios + "/";

            Object alumno = restTemplate.getForObject(finalUrlUsuarios + nuevo.getIdEstudiante(), Object.class);
            
            if (alumno == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El alumno ID " + nuevo.getIdEstudiante() + " no existe.");
            }
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error al conectar con el servicio de Usuarios: " + e.getMessage());
        }

        boolean existe = inscripcionRepository
                .findByIdEstudianteAndIdCurso(nuevo.getIdEstudiante(), nuevo.getIdCurso().longValue())
                .isPresent();

        if (existe) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estudiante ya está inscrito en este curso.");
        }

        // C. GUARDAR
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setIdEstudiante(nuevo.getIdEstudiante());
        inscripcion.setIdCurso(nuevo.getIdCurso().longValue());
        
        return inscripcionRepository.save(inscripcion);
    }


    public Inscripcion actualizarProgreso(Long idInscripcion, Double porcentaje) {
        Inscripcion inscripcion = inscripcionRepository.findById(idInscripcion.intValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscripción no encontrada"));

        if (porcentaje < 0) porcentaje = 0.0;
        if (porcentaje > 100) porcentaje = 100.0;

        inscripcion.setProgreso(porcentaje);
        inscripcion.setUltimoAcceso(LocalDateTime.now());

        if (porcentaje >= 100.0) {
            inscripcion.setEstado(EstadoInscripcion.FINALIZADO);
            if (inscripcion.getFechaFin() == null) {
                inscripcion.setFechaFin(LocalDateTime.now());
            }
        } else {
            inscripcion.setEstado(EstadoInscripcion.CURSANDO);
            inscripcion.setFechaFin(null);
        }

        return inscripcionRepository.save(inscripcion);
    }

    public String eliminar(Long id) {
        if (inscripcionRepository.existsById(id.intValue())) {
            inscripcionRepository.deleteById(id.intValue());
            return "Inscripción eliminada correctamente.";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscripción no encontrada.");
        }
    }

    public List<Inscripcion> obtenerTodas() {
        return inscripcionRepository.findAll();
    }
}