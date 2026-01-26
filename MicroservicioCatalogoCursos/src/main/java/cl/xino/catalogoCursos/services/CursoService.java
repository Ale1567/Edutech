package cl.xino.catalogoCursos.services;

import cl.xino.catalogoCursos.models.entities.Curso;
import cl.xino.catalogoCursos.models.requests.ContenidoRequest;
import cl.xino.catalogoCursos.models.requests.CursoCompletoRequest;
import cl.xino.catalogoCursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; // Importante
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.ArrayList; // Para el fallback
import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;
    
    @Autowired
    private RestTemplate restTemplate;


    @Value("${microservicio.contenidos.url:http://localhost:8082}")
    private String contenidosUrl;

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

        String url = contenidosUrl + "/contenidos/curso/" + cursoId;
        
        List<ContenidoRequest> contenidos;
        try {
            contenidos = restTemplate.exchange(
                url, 
                HttpMethod.GET, 
                null, 
                new ParameterizedTypeReference<List<ContenidoRequest>>(){}
            ).getBody();
        } catch (Exception e) {
            System.err.println("Error llamando a Contenidos: " + e.getMessage());
            contenidos = new ArrayList<>();
        }

        return new CursoCompletoRequest(curso, contenidos);
    }
}