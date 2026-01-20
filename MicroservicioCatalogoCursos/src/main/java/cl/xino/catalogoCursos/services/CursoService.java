package cl.xino.catalogoCursos.services;

import cl.xino.catalogoCursos.models.entities.Curso;
import cl.xino.catalogoCursos.models.requests.ContenidoRequest;
import cl.xino.catalogoCursos.models.requests.CursoCompletoRequest;
import cl.xino.catalogoCursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    public List<Curso> obtenerTodos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> obtenerPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Curso guardarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public void eliminarCurso(Long id) {
        cursoRepository.deleteById(id);
    }
    
    public List<Curso> buscarPorCategoria(String categoria) {
        return cursoRepository.findByCategoria(categoria);
    }
    public CursoCompletoRequest obtenerCursoConContenidos(Long cursoId) {

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));


        String url = "http://localhost:8082/contenidos/curso/" + cursoId;
        
        List<ContenidoRequest> contenidos = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ContenidoRequest>>(){}).getBody();

        return new CursoCompletoRequest(curso, contenidos);
    }
}