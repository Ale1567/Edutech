package com.example.gestionConsultas;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.gestionConsultas.models.entities.Consulta;
import com.example.gestionConsultas.models.request.ConsultaRequest;
import com.example.gestionConsultas.repositories.ConsultaRepository;
import com.example.gestionConsultas.services.ConsultaService;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @InjectMocks
    private ConsultaService consultaService;

    @Test
    void testCrearConsulta() {
        ConsultaRequest request = new ConsultaRequest();
        request.setTitulo("Duda Java");
        request.setMensaje("¿Cómo funciona Spring?");
        request.setEstudianteId(1L);

        Consulta consultaMock = new Consulta();
        consultaMock.setTitulo("Duda Java");
        
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consultaMock);

        Consulta resultado = consultaService.crearConsulta(request);

        assertNotNull(resultado);
        assertEquals("Duda Java", resultado.getTitulo());
    }

    @Test
    void testResponderConsulta() {
        // Escenario: Existe la consulta
        Consulta consultaExistente = new Consulta();
        consultaExistente.setIdConsulta(1L);
        consultaExistente.setEstado("PENDIENTE");

        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consultaExistente));
        when(consultaRepository.save(any(Consulta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Consulta resultado = consultaService.responder(1L, "Esta es la respuesta");

        assertNotNull(resultado);
        assertEquals("RESPONDIDA", resultado.getEstado());
        assertEquals("Esta es la respuesta", resultado.getRespuesta());
    }
}