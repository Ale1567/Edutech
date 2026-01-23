package com.example.inscripciones.repositories;

import com.example.inscripciones.models.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {
    
    List<Inscripcion> findByIdEstudiante(int idEstudiante);

    Optional<Inscripcion> findByIdEstudianteAndIdCurso(int idEstudiante, Long idCurso);
}