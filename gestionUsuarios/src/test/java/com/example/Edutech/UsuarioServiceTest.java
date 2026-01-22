package com.example.Edutech;

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

import com.example.Edutech.models.entities.Usuario;
import com.example.Edutech.repositories.UsuarioRepository;
import com.example.Edutech.services.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testObtencionUsuarioPorId() {

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNombre("Juan");
        usuario.setEmail("juan@test.com");
        usuario.setEstado("ACTIVO");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.obtenerUsuarioPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdUsuario());
        assertEquals("Juan", resultado.getNombre());
        assertEquals("juan@test.com", resultado.getEmail());
        assertEquals("ACTIVO", resultado.getEstado());

        verify(usuarioRepository, times(1)).findById(1);
    }
    
}
