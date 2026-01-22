package com.example.gestionIncidencias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.gestionIncidencias.models.entities.Incidencia;
import com.example.gestionIncidencias.repositories.IncidenciaRepository;
import com.example.gestionIncidencias.services.IncidenciaService;

@ExtendWith(MockitoExtension.class)
public class TestIncidenciaService {

    @Mock
    private IncidenciaRepository incidenciaRepository;

    @InjectMocks
    private IncidenciaService incidenciaService;

    @Test
    void testObtencionUsuarioPorId() {

        Incidencia incidencia = new Incidencia();
        incidencia.setIdIncidencias(1);
        incidencia.setDescripcion("Profesor faltante");
        incidencia.setEstado("En revision");
        incidencia.setPrioridad(1);


        when(incidenciaRepository.findById(1)).thenReturn(Optional.of(incidencia));

        Incidencia resultado = incidenciaService.obtenerIncidenciasPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdIncidencias());
        assertEquals("Profesor faltante", resultado.getDescripcion());
        assertEquals("En revision", resultado.getEstado());
        assertEquals(1, resultado.getPrioridad());

        verify(incidenciaRepository, times(1)).findById(1);
    }
    
}
