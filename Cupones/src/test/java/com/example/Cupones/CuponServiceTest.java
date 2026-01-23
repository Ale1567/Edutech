package com.example.Cupones;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Cupones.models.entities.Cupon;
import com.example.Cupones.models.request.AgregarCupon;
import com.example.Cupones.repositories.CuponRepository;
import com.example.Cupones.service.CuponService;

@ExtendWith(MockitoExtension.class)
class CuponServiceTest {

    @Mock
    private CuponRepository cuponRepository;

    @InjectMocks
    private CuponService cuponService;

    @Test
    void testGuardarCupon() {
        // Datos de prueba
        AgregarCupon request = new AgregarCupon();
        request.setCodigo("DESCUENTO2026");
        request.setPorcentajeDescuento(15);

        Cupon cuponGuardado = new Cupon();
        cuponGuardado.setIdCupon(1L);
        cuponGuardado.setCodigo("DESCUENTO2026");
        cuponGuardado.setPorcentajeDescuento(15);
        cuponGuardado.setActivo(true);

        // Simulamos el repositorio
        when(cuponRepository.save(any(Cupon.class))).thenReturn(cuponGuardado);

        // Ejecutamos el servicio
        Cupon resultado = cuponService.guardar(request);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals("DESCUENTO2026", resultado.getCodigo());
        assertTrue(resultado.getActivo());
    }

    @Test
    void testBuscarPorCodigo() {
        Cupon cupon = new Cupon();
        cupon.setCodigo("TEST");
        
        when(cuponRepository.findByCodigo("TEST")).thenReturn(Optional.of(cupon));

        Optional<Cupon> resultado = cuponService.buscarPorCodigo("TEST");

        assertTrue(resultado.isPresent());
        assertEquals("TEST", resultado.get().getCodigo());
    }
}