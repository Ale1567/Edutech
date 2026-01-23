package com.example.microservicioEvaluaciones;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.microservicioEvaluaciones.models.dto.CursoDto;
import com.example.microservicioEvaluaciones.models.dto.EvaluacionRequest;
import com.example.microservicioEvaluaciones.models.dto.EvaluacionResponse;
import com.example.microservicioEvaluaciones.models.entities.Evaluacion;
import com.example.microservicioEvaluaciones.repositories.EvaluacionRepository;
import com.example.microservicioEvaluaciones.exceptions.BusinessRuleException;
import com.example.microservicioEvaluaciones.exceptions.ResourceNotFoundException;
import com.example.microservicioEvaluaciones.services.EvaluacionService;

@ExtendWith(MockitoExtension.class)
class EvaluacionServiceTest {

    @Mock
    private EvaluacionRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EvaluacionService service;

    @Test
    void testCrearEvaluacion_Exito() {

        Long cursoId = 1L;
        
        EvaluacionRequest request = new EvaluacionRequest();
        request.setTitulo("Prueba 1");
        request.setCursoId(cursoId);
        request.setPorcentaje(20);
        request.setFechaInicio(LocalDateTime.now().plusDays(1));
        request.setFechaFin(LocalDateTime.now().plusDays(2));

        CursoDto cursoFake = new CursoDto();
        cursoFake.setTitulo("Java Avanzado");
        when(restTemplate.getForObject(contains("/api/cursos/"), eq(CursoDto.class)))
            .thenReturn(cursoFake);

        when(repository.findByCursoId(cursoId)).thenReturn(new ArrayList<>());

        when(repository.save(any(Evaluacion.class))).thenAnswer(i -> {
            Evaluacion e = (Evaluacion) i.getArguments()[0];
            e.setId(100L); 
            return e;
        });

        EvaluacionResponse respuesta = service.crearEvaluacion(request);

        assertNotNull(respuesta);
        assertEquals("Prueba 1", respuesta.getTitulo());
        assertEquals("Java Avanzado", respuesta.getNombreCurso()); 
        

        verify(repository).save(any(Evaluacion.class));
    }


    @Test
    void testCrearEvaluacion_FallaCursoNoExiste() {

        EvaluacionRequest request = new EvaluacionRequest();
        request.setCursoId(99L);

        HttpClientErrorException notFoundEx = HttpClientErrorException.create(
            HttpStatus.NOT_FOUND, "Not Found", org.springframework.http.HttpHeaders.EMPTY, null, null
        );

        when(restTemplate.getForObject(anyString(), eq(CursoDto.class)))
            .thenThrow(notFoundEx);

        assertThrows(ResourceNotFoundException.class, () -> {
            service.crearEvaluacion(request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void testCrearEvaluacion_FallaSumaPorcentaje() {

        Long cursoId = 1L;
        EvaluacionRequest request = new EvaluacionRequest();
        request.setCursoId(cursoId);
        request.setPorcentaje(30);
        request.setFechaInicio(LocalDateTime.now());
        request.setFechaFin(LocalDateTime.now().plusDays(1));

        when(restTemplate.getForObject(anyString(), eq(CursoDto.class))).thenReturn(new CursoDto());

        Evaluacion evalExistente = new Evaluacion();
        evalExistente.setPorcentaje(80);
        List<Evaluacion> listaExistente = List.of(evalExistente);

        when(repository.findByCursoId(cursoId)).thenReturn(listaExistente);

        BusinessRuleException ex = assertThrows(BusinessRuleException.class, () -> {
            service.crearEvaluacion(request);
        });

        assertTrue(ex.getMessage().contains("100%"), "El mensaje debe mencionar el límite del 100%");
    }

    @Test
    void testCrearEvaluacion_FallaFechasMalas() {

        EvaluacionRequest request = new EvaluacionRequest();
        request.setCursoId(1L);

        request.setFechaInicio(LocalDateTime.now().plusDays(5));
        request.setFechaFin(LocalDateTime.now().plusDays(1)); 

        when(restTemplate.getForObject(anyString(), eq(CursoDto.class))).thenReturn(new CursoDto());

        assertThrows(BusinessRuleException.class, () -> {
            service.crearEvaluacion(request);
        });
    }
}