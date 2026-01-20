package cl.xino.catalogoCursos.models.requests;

import  cl.xino.catalogoCursos.models.entities.Curso;
import java.util.List;

public class CursoCompletoRequest {
    private Curso curso;
    private List<ContenidoRequest> contenidos;
    
    public CursoCompletoRequest(Curso curso, List<ContenidoRequest> contenidos) {
        this.curso = curso;
        this.contenidos = contenidos;
    }
    
    public Curso getCurso() { return curso; }
    public List<ContenidoRequest> getContenidos() { return contenidos; }
}