package com.example.inscripciones.services;

import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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

    // URL del microservicio de cursos (Ajusta el puerto si es necesario)
    private final String URL_CURSOS = "http://localhost:8081/api/cursos/";

    // --- 1. OBTENER CURSOS DE UN ALUMNO (Enriquecido con nombres) ---
    public List<InscripcionDetalleDto> obtenerCursosDeEstudiante(int idEstudiante) {
        List<Inscripcion> inscripciones = inscripcionRepository.findByIdEstudiante(idEstudiante);

        return inscripciones.stream().map(inscripcion -> {
            InscripcionDetalleDto dto = new InscripcionDetalleDto();
            
            // Datos locales
            dto.setIdInscripcion((long) inscripcion.getIdInscripcion());
            dto.setProgreso(inscripcion.getProgreso());
            dto.setEstado(inscripcion.getEstado().toString());
            dto.setUltimoAcceso(inscripcion.getUltimoAcceso());

            // Datos externos (Microservicio Cursos)
            try {
                // Buscamos el nombre del curso usando su ID
                CursoDto curso = restTemplate.getForObject(URL_CURSOS + inscripcion.getIdCurso(), CursoDto.class);
                if (curso != null) {
                    dto.setNombreCurso(curso.getNombre());
                }
            } catch (Exception e) {
                // Si falla la conexión, ponemos un texto por defecto
                dto.setNombreCurso("Curso ID: " + inscripcion.getIdCurso() + " (Info no disponible)");
            }
            
            return dto;
        }).collect(Collectors.toList());
    }

    // --- 2. REGISTRAR INSCRIPCIÓN (Con validación de duplicados) ---
    public Inscripcion registrarInscripcion(AgregarInscripcion nuevo) {
        // Validar si ya existe la inscripción
        boolean existe = inscripcionRepository
                .findByIdEstudianteAndIdCurso(nuevo.getIdEstudiante(), nuevo.getIdCurso().longValue())
                .isPresent();

        if (existe) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estudiante ya está inscrito en este curso.");
        }

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setIdEstudiante(nuevo.getIdEstudiante());
        inscripcion.setIdCurso(nuevo.getIdCurso().longValue());
        
        // Los valores por defecto (progreso 0.0, estado CURSANDO) se ponen solos en la Entidad
        
        return inscripcionRepository.save(inscripcion);
    }

    // --- 3. ACTUALIZAR PROGRESO (La lógica del seguimiento) ---
    public Inscripcion actualizarProgreso(Long idInscripcion, Double porcentaje) {
        Inscripcion inscripcion = inscripcionRepository.findById(idInscripcion.intValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscripción no encontrada"));

        // Validamos rango lógico
        if (porcentaje < 0) porcentaje = 0.0;
        if (porcentaje > 100) porcentaje = 100.0;

        inscripcion.setProgreso(porcentaje);
        inscripcion.setUltimoAcceso(LocalDateTime.now());

        // Si llega al 100%, marcamos como finalizado
        if (porcentaje >= 100.0) {
            inscripcion.setEstado(EstadoInscripcion.FINALIZADO);
            if (inscripcion.getFechaFin() == null) {
                inscripcion.setFechaFin(LocalDateTime.now());
            }
        } else {
            // Si por error bajó del 100%, vuelve a cursando
            inscripcion.setEstado(EstadoInscripcion.CURSANDO);
            inscripcion.setFechaFin(null);
        }

        return inscripcionRepository.save(inscripcion);
    }

    // --- 4. ELIMINAR (Ajustado para recibir Long) ---
    public String eliminar(Long id) { // Cambié int por Long
        if (inscripcionRepository.existsById(id.intValue())) {
            inscripcionRepository.deleteById(id.intValue());
            return "Inscripción eliminada correctamente.";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscripción no encontrada.");
        }
    }
    
    // Método auxiliar por si necesitas listar todas las inscripciones del sistema (Admin)
    public List<Inscripcion> obtenerTodas() {
        return inscripcionRepository.findAll();
    }
}