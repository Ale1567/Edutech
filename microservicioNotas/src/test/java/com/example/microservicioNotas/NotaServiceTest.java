package com.example.microservicioNotas;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.microservicioNotas.models.dto.EvaluacionDto;
import com.example.microservicioNotas.models.dto.NotaRequest;
import com.example.microservicioNotas.models.dto.UsuarioDto;
import com.example.microservicioNotas.models.entities.Nota;
import com.example.microservicioNotas.repositories.NotaRepository;
import com.example.microservicioNotas.services.NotaService;

@ExtendWith(MockitoExtension.class)
class NotaServiceTest {

    @Mock
    private NotaRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NotaService service;

    @Test
    void testGuardarNota_Exito() {

        NotaRequest request = new NotaRequest();
        request.setAlumnoId(1);
        request.setEvaluacionId(10L);
        request.setValor(6.5);
        request.setObservacion("Muy bien");


        when(repository.existsByAlumnoIdAndEvaluacionId(1, 10L)).thenReturn(false);


        UsuarioDto alumnoFake = new UsuarioDto();
        alumnoFake.setIdUsuario(1);
        alumnoFake.setRol("ALUMNO");
        when(restTemplate.getForObject(contains("/api/usuarios/"), eq(UsuarioDto.class)))
            .thenReturn(alumnoFake);


        EvaluacionDto evalFake = new EvaluacionDto();
        evalFake.setId(10L);
        evalFake.setFechaFin(LocalDateTime.now().plusDays(5));
        when(restTemplate.getForObject(contains("/api/evaluaciones/"), eq(EvaluacionDto.class)))
            .thenReturn(evalFake);

        when(repository.save(any(Nota.class))).thenAnswer(i -> {
            Nota n = (Nota) i.getArguments()[0];
            n.setIdNota(777L);
            return n;
        });

        Nota resultado = service.guardarNota(request);

        assertNotNull(resultado);
        assertEquals(777L, resultado.getIdNota());
        assertEquals(6.5, resultado.getCalificacion());
        verify(repository).save(any(Nota.class));
    }


    @Test
    void testGuardarNota_FallaSiYaExiste() {
        NotaRequest request = new NotaRequest();
        request.setAlumnoId(1);
        request.setEvaluacionId(10L);

        when(repository.existsByAlumnoIdAndEvaluacionId(1, 10L)).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.guardarNota(request);
        });

        assertTrue(ex.getMessage().contains("ya tiene nota"));

        verifyNoInteractions(restTemplate);
    }

    @Test
    void testGuardarNota_FallaAlumnoNoExiste() {
        NotaRequest request = new NotaRequest();
        request.setAlumnoId(99);
        request.setEvaluacionId(10L);

        when(repository.existsByAlumnoIdAndEvaluacionId(anyInt(), anyLong())).thenReturn(false);

        HttpClientErrorException notFound = HttpClientErrorException.create(
            HttpStatus.NOT_FOUND, "Not Found", org.springframework.http.HttpHeaders.EMPTY, null, null
        );

        when(restTemplate.getForObject(contains("/api/usuarios/"), eq(UsuarioDto.class)))
            .thenThrow(notFound);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.guardarNota(request);
        });

        assertTrue(ex.getMessage().contains("Alumno no encontrado"));
    }

    @Test
    void testGuardarNota_FallaRolNoEsAlumno() {
        NotaRequest request = new NotaRequest();
        request.setAlumnoId(1);
        request.setEvaluacionId(10L);

        when(repository.existsByAlumnoIdAndEvaluacionId(1, 10L)).thenReturn(false);

        UsuarioDto profeFake = new UsuarioDto();
        profeFake.setRol("PROFESOR");
        
        when(restTemplate.getForObject(contains("/api/usuarios/"), eq(UsuarioDto.class)))
            .thenReturn(profeFake);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.guardarNota(request);
        });

        assertTrue(ex.getMessage().contains("no tiene el rol de ALUMNO"));
    }

    @Test
    void testGuardarNota_FallaEvaluacionCerrada() {
        NotaRequest request = new NotaRequest();
        request.setAlumnoId(1);
        request.setEvaluacionId(10L);

        when(repository.existsByAlumnoIdAndEvaluacionId(1, 10L)).thenReturn(false);

        UsuarioDto alumno = new UsuarioDto();
        alumno.setRol("ALUMNO");
        when(restTemplate.getForObject(contains("/api/usuarios/"), eq(UsuarioDto.class))).thenReturn(alumno);

        EvaluacionDto evalCerrada = new EvaluacionDto();
        evalCerrada.setId(10L);
        evalCerrada.setFechaFin(LocalDateTime.now().minusDays(1));

        when(restTemplate.getForObject(contains("/api/evaluaciones/"), eq(EvaluacionDto.class)))
            .thenReturn(evalCerrada);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.guardarNota(request);
        });

        assertTrue(ex.getMessage().contains("ya cerró"));
        verify(repository, never()).save(any());
    }
}