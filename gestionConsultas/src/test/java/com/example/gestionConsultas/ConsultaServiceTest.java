package com.example.gestionConsultas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.example.gestionConsultas.models.entities.Consulta;
import com.example.gestionConsultas.repositories.ConsultaRepository;
import com.example.gestionConsultas.services.ConsultaService;

import lombok.Data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ConsultaServiceTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @InjectMocks
    private ConsultaService consultaService;

    @Test
    public void alResponderConsulta_debeCambiarEstadoARespondida() {
        // Mock de una consulta existente
        Consulta consultaExistente = new Consulta();
        consultaExistente.setIdConsulta(1L);
        consultaExistente.setEstado("PENDIENTE");

        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consultaExistente));
        when(consultaRepository.save(any(Consulta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Ejecutar respuesta
        Consulta resultado = consultaService.responder(1L, "Respuesta de prueba");

        // Verificar cambio de estado
        assertEquals("RESPONDIDA", resultado.getEstado(), "El estado debe cambiar a RESPONDIDA tras responder");
        assertEquals("Respuesta de prueba", resultado.getRespuesta());
    }
}