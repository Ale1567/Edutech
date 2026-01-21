package com.example.gestionRespaldos;

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

import com.example.gestionRespaldos.models.entities.Copia;
import com.example.gestionRespaldos.repositories.CopiaRepository;
import com.example.gestionRespaldos.services.CopiaService;

@ExtendWith(MockitoExtension.class)
public class TestCopiaService {
    @Mock
    private CopiaRepository copiaRepository;

    @InjectMocks
    private CopiaService copiaService;

    @Test
    void testObtencionCopiaPorId() {

        Copia copia = new Copia();
        copia.setId_copia(1);
        copia.setNombre("Copia 21/01/2026");
        copia.setVersion(1.1);
 

        when(copiaRepository.findById(1)).thenReturn(Optional.of(copia));

        Copia resultado = copiaService.obtenerCopiaPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId_copia());
        assertEquals("Juan", resultado.getNombre());
        assertEquals("juan@test.com", resultado.getVersion());

        verify(copiaRepository, times(1)).findById(1);
    }
    
}
