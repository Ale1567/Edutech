package com.example.microservicioNotas.repositories;

import com.example.microservicioNotas.models.entities.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    List<Nota> findByAlumnoId(Integer alumnoId);
    List<Nota> findByEvaluacionId(Long evaluacionId);
    boolean existsByAlumnoIdAndEvaluacionId(Integer alumnoId, Long evaluacionId);
}