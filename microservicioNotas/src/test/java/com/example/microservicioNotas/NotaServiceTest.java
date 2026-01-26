package com.example.microservicioNotas;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

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

    // --- TESTS ORIGINALES (GUARDAR NOTA) ---

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

        assertThrows(RuntimeException.class, () -> service.guardarNota(request));
        verifyNoInteractions(restTemplate);
    }

    // --- NUEVOS TESTS (ACTUALIZAR NOTA) ---

    @Test
    void testActualizarNota_Exito() {
        // Mock de nota existente
        Nota notaExistente = new Nota();
        notaExistente.setIdNota(1L);
        notaExistente.setEvaluacionId(10L);
        notaExistente.setCalificacion(4.0);

        // Mock de evaluación abierta
        EvaluacionDto evalAbierta = new EvaluacionDto();
        evalAbierta.setFechaFin(LocalDateTime.now().plusDays(2));

        when(repository.findById(1L)).thenReturn(Optional.of(notaExistente));
        when(restTemplate.getForObject(contains("/api/evaluaciones/"), eq(EvaluacionDto.class)))
            .thenReturn(evalAbierta);
        when(repository.save(any(Nota.class))).thenReturn(notaExistente);

        // Ejecución
        Nota resultado = service.actualizarNota(1L, 7.0, "Subió la nota por décimas");

        assertNotNull(resultado);
        assertEquals(7.0, resultado.getCalificacion());
        verify(repository).save(any(Nota.class));
    }

    @Test
    void testActualizarNota_FallaSiEvaluacionCerrada() {
        Nota notaExistente = new Nota();
        notaExistente.setIdNota(1L);
        notaExistente.setEvaluacionId(10L);

        // Mock de evaluación cerrada (ayer)
        EvaluacionDto evalCerrada = new EvaluacionDto();
        evalCerrada.setFechaFin(LocalDateTime.now().minusDays(1));

        when(repository.findById(1L)).thenReturn(Optional.of(notaExistente));
        when(restTemplate.getForObject(contains("/api/evaluaciones/"), eq(EvaluacionDto.class)))
            .thenReturn(evalCerrada);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.actualizarNota(1L, 6.0, "Intento ilegal");
        });

        assertTrue(ex.getMessage().contains("ya cerró"));
        verify(repository, never()).save(any());
    }

    @Test
    void testActualizarNota_FallaSiNotaNoExiste() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.actualizarNota(999L, 5.0, "Nota fantasma");
        });
        
        verifyNoInteractions(restTemplate);
    }
}