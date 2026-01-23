package com.example.Resena;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Resena.models.entities.Resena;
import com.example.Resena.repositories.ResenaRepository;
import com.example.Resena.services.ResenaService;

@ExtendWith(MockitoExtension.class)
class ResenaServiceTest {

    @Mock
    private ResenaRepository resenaRepository;

    @InjectMocks
    private ResenaService resenaService;

    @Test
    void testCrearResena() {
        Resena nueva = new Resena();
        nueva.setComentario("Excelente curso");
        nueva.setCalificacion(5);

        when(resenaRepository.save(any(Resena.class))).thenReturn(nueva);

        Resena resultado = resenaService.crearResena(nueva);

        assertNotNull(resultado);
        assertEquals(5, resultado.getCalificacion());
    }

    @Test
    void testObtenerResenasPorCurso() {
        Resena r1 = new Resena();
        r1.setCursoId(1L);
        Resena r2 = new Resena();
        r2.setCursoId(1L);

        when(resenaRepository.findByCursoId(1L)).thenReturn(Arrays.asList(r1, r2));

        List<Resena> lista = resenaService.obtenerResenasPorCurso(1L);

        assertEquals(2, lista.size());
    }
}