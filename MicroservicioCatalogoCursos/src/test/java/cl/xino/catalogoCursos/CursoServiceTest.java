package cl.xino.catalogoCursos;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import cl.xino.catalogoCursos.models.entities.Curso;
import cl.xino.catalogoCursos.models.requests.ContenidoRequest;
import cl.xino.catalogoCursos.models.requests.CursoCompletoRequest;
import cl.xino.catalogoCursos.repositories.CursoRepository;
import cl.xino.catalogoCursos.services.CursoService;

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CursoService service;

    @Test
    void testGuardarCurso() {
        Curso curso = new Curso();
        curso.setTitulo("Curso Spring Boot Extremo");
        curso.setCategoria("Backend");
        curso.setPrecio(15000);
        curso.setInstructorId(10);
        curso.setFechaInicio(LocalDateTime.now());

        when(cursoRepository.save(any(Curso.class))).thenAnswer(i -> {
            Curso c = (Curso) i.getArguments()[0];
            c.setIdCurso(999L);
            return c;
        });

        Curso guardado = service.guardarCurso(curso);

        assertNotNull(guardado);
        assertEquals(999L, guardado.getIdCurso());
        assertEquals("Curso Spring Boot Extremo", guardado.getTitulo());
        verify(cursoRepository).save(any(Curso.class));
    }

    @Test
    void testObtenerCursoConContenidos_Exito() {
        Long idBuscado = 50L;

        Curso cursoMock = new Curso();
        cursoMock.setIdCurso(idBuscado);
        cursoMock.setTitulo("Arquitectura Limpia");
        cursoMock.setDescripcion("Curso avanzado");
        
        when(cursoRepository.findById(idBuscado)).thenReturn(Optional.of(cursoMock));

        List<ContenidoRequest> listaContenidos = new ArrayList<>();
        ContenidoRequest cReq = new ContenidoRequest();
        listaContenidos.add(cReq);

        ResponseEntity<List<ContenidoRequest>> responseEntity = new ResponseEntity<>(listaContenidos, HttpStatus.OK);

        when(restTemplate.exchange(
            contains("/contenidos/curso/" + idBuscado),
            eq(HttpMethod.GET),
            eq(null),
            any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);


        CursoCompletoRequest resultado = service.obtenerCursoConContenidos(idBuscado);


        assertNotNull(resultado);

        assertEquals(idBuscado, resultado.getCurso().getIdCurso());
        assertEquals("Arquitectura Limpia", resultado.getCurso().getTitulo());
        
        assertNotNull(resultado.getContenidos());
        assertEquals(1, resultado.getContenidos().size());
    }

    @Test
    void testObtenerCursoConContenidos_CursoNoExiste() {

        Long idBuscado = 666L;
        when(cursoRepository.findById(idBuscado)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.obtenerCursoConContenidos(idBuscado);
        });

        assertEquals("Curso no encontrado", ex.getMessage());

        verifyNoInteractions(restTemplate);
    }
}