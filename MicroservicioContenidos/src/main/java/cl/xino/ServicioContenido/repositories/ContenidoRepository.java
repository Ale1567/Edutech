package cl.xino.ServicioContenido.repositories;

import cl.xino.ServicioContenido.models.entities.Contenido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContenidoRepository extends JpaRepository<Contenido, Long> {
    
    List<Contenido> findByIdCurso(Long idCurso);
}