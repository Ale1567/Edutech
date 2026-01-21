package com.example.gestionNotificaciones;

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

import com.example.gestionNotificaciones.models.entities.Notificacion;
import com.example.gestionNotificaciones.repositories.NotificacionRepository;
import com.example.gestionNotificaciones.services.NotificacionService;

@ExtendWith(MockitoExtension.class)
public class TestNotificacionService {
    
    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    @Test
    void testObtencionNotificacionPorId() {

        Notificacion notificacion = new Notificacion();
        notificacion.setId_notif(1);
        notificacion.setTitulo("Mensaje de profesor");
        notificacion.setDescripcion("No clase hoy");

        when(notificacionRepository.findById(1)).thenReturn(Optional.of(notificacion));

        Notificacion resultado = notificacionService.obtenerNotificacionPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId_notif());
        assertEquals("Mensaje de profesor", resultado.getTitulo());
        assertEquals("No clase hoy", resultado.getDescripcion());

        verify(notificacionRepository, times(1)).findById(1);
    }
    
}
