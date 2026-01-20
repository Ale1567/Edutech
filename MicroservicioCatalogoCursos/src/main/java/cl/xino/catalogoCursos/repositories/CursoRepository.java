package cl.xino.catalogoCursos.repositories;


import cl.xino.catalogoCursos.models.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByCategoria(String categoria);
}