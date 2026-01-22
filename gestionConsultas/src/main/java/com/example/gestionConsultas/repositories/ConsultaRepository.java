package com.example.gestionConsultas.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.gestionConsultas.models.entities.Consulta;

@Repository

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    
    List<Consulta> findByEstudianteId(Long estudianteId);
    List<Consulta> findByEstado(String estado);
}
