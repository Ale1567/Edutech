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

@ExtendWith(MockitoExtension.class) // Habilita Mockito
class InscripcionServiceTest {

    @Mock // Simulamos el Repositorio (No queremos ir a la BD real)
    private InscripcionRepository repository;

    @Mock // Simulamos RestTemplate (No queremos llamar al otro microservicio)
    private RestTemplate restTemplate;

    @InjectMocks // Inyectamos los mocks dentro de nuestro Servicio real
    private InscripcionService service;

    @Test
    void testActualizarProgresoA100_DebeFinalizarCurso() {
        // 1. ARRANGE (Preparar datos de prueba)
        Long idPrueba = 1L;
        
        // Creamos una inscripción simulada en estado CURSANDO
        Inscripcion inscripcionSimulada = new Inscripcion();
        inscripcionSimulada.setIdInscripcion(idPrueba.intValue());
        inscripcionSimulada.setProgreso(50.0);
        inscripcionSimulada.setEstado(EstadoInscripcion.CURSANDO);

        // Le enseñamos al Mock qué hacer: "Cuando busquen el ID 1, devuelve mi objeto simulado"
        when(repository.findById(idPrueba.intValue())).thenReturn(Optional.of(inscripcionSimulada));
        
        // "Cuando guarden algo, devuélvelo tal cual"
        when(repository.save(any(Inscripcion.class))).thenAnswer(i -> i.getArguments()[0]);

        // 2. ACT (Ejecutar la acción)
        // Probamos subir el progreso al 100.0
        Inscripcion resultado = service.actualizarProgreso(idPrueba, 100.0);

        // 3. ASSERT (Verificar resultados)
        // Verificamos que el porcentaje sea 100
        assertEquals(100.0, resultado.getProgreso());
        
        // ¡LO IMPORTANTE! Verificamos que el estado cambió automáticamente a FINALIZADO
        assertEquals(EstadoInscripcion.FINALIZADO, resultado.getEstado());
        
        // Verificamos que se guardó la fecha de fin
        assertNotNull(resultado.getFechaFin());

        // Verificamos que el repositorio realmente se llamó una vez para guardar
        verify(repository, times(1)).save(inscripcionSimulada);
        
        System.out.println("Test 1 Pasado: El curso se finalizó correctamente.");
    }

    @Test
    void testActualizarProgresoA50_SigueCursando() {
        // 1. ARRANGE
        Long idPrueba = 2L;
        Inscripcion inscripcionSimulada = new Inscripcion();
        inscripcionSimulada.setIdInscripcion(idPrueba.intValue());
        inscripcionSimulada.setEstado(EstadoInscripcion.CURSANDO);

        when(repository.findById(idPrueba.intValue())).thenReturn(Optional.of(inscripcionSimulada));
        when(repository.save(any(Inscripcion.class))).thenAnswer(i -> i.getArguments()[0]);

        // 2. ACT
        Inscripcion resultado = service.actualizarProgreso(idPrueba, 50.0);

        // 3. ASSERT
        assertEquals(50.0, resultado.getProgreso());
        assertEquals(EstadoInscripcion.CURSANDO, resultado.getEstado()); // Debe seguir cursando
        assertNull(resultado.getFechaFin()); // No debe tener fecha fin

        System.out.println("Test 2 Pasado: El curso sigue en progreso.");
    }
}