package com.example.inscripciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.inscripciones.models.entities.Inscripcion;
import com.example.inscripciones.models.request.AgregarInscripcion;
import com.example.inscripciones.repositories.InscripcionRepository;
import com.example.inscripciones.services.InscripcionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InscripcionServiceTest {

    @Mock
    private InscripcionRepository inscripcionRepository;

    @InjectMocks
    private InscripcionService inscripcionService;

    @Test
    public void registrarInscripcion_debeGuardarConIdsCorrectos() {
        AgregarInscripcion request = new AgregarInscripcion();
        request.setIdEstudiante(10);
        request.setIdCurso(20);

        when(inscripcionRepository.save(any(Inscripcion.class))).thenAnswer(i -> i.getArgument(0));

        Inscripcion resultado = inscripcionService.registrarInscripcion(request);

        assertEquals(10L, resultado.getIdEstudiante());
        assertEquals(20L, resultado.getIdCurso());
    }
}