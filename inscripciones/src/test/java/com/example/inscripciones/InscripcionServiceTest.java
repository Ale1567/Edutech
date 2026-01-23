package com.example.inscripciones;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.example.inscripciones.models.enums.EstadoInscripcion;
import com.example.inscripciones.models.entities.Inscripcion;
import com.example.inscripciones.repositories.InscripcionRepository;
import com.example.inscripciones.services.InscripcionService;

@ExtendWith(MockitoExtension.class) 
class InscripcionServiceTest {

    @Mock 
    private InscripcionRepository repository;

    @Mock 
    private RestTemplate restTemplate;

    @InjectMocks 
    private InscripcionService service;

    @Test
    void testActualizarProgresoA100_DebeFinalizarCurso() {

        Long idPrueba = 1L;
        

        Inscripcion inscripcionSimulada = new Inscripcion();
        inscripcionSimulada.setIdInscripcion(idPrueba.intValue());
        inscripcionSimulada.setProgreso(50.0);
        inscripcionSimulada.setEstado(EstadoInscripcion.CURSANDO);

        when(repository.findById(idPrueba.intValue())).thenReturn(Optional.of(inscripcionSimulada));
        
        when(repository.save(any(Inscripcion.class))).thenAnswer(i -> i.getArguments()[0]);

        Inscripcion resultado = service.actualizarProgreso(idPrueba, 100.0);


        assertEquals(100.0, resultado.getProgreso());
        

        assertEquals(EstadoInscripcion.FINALIZADO, resultado.getEstado());
        

        assertNotNull(resultado.getFechaFin());


        verify(repository, times(1)).save(inscripcionSimulada);
        
        System.out.println("Test 1 Pasado: El curso se finalizó correctamente.");
    }

    @Test
    void testActualizarProgresoA50_SigueCursando() {

        Long idPrueba = 2L;
        Inscripcion inscripcionSimulada = new Inscripcion();
        inscripcionSimulada.setIdInscripcion(idPrueba.intValue());
        inscripcionSimulada.setEstado(EstadoInscripcion.CURSANDO);

        when(repository.findById(idPrueba.intValue())).thenReturn(Optional.of(inscripcionSimulada));
        when(repository.save(any(Inscripcion.class))).thenAnswer(i -> i.getArguments()[0]);


        Inscripcion resultado = service.actualizarProgreso(idPrueba, 50.0);


        assertEquals(50.0, resultado.getProgreso());
        assertEquals(EstadoInscripcion.CURSANDO, resultado.getEstado());
        assertNull(resultado.getFechaFin());

        System.out.println("Test 2 Pasado: El curso sigue en progreso.");
    }
}