package com.example.gestionReportes;

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

import com.example.gestionReportes.models.entities.Reporte;
import com.example.gestionReportes.repositories.ReporteRepository;
import com.example.gestionReportes.services.ReporteService;

@ExtendWith(MockitoExtension.class)
public class TestReporteService {

    @Mock
    private ReporteRepository reporteRepository;

    @InjectMocks
    private ReporteService reporteService;

    @Test
    void testObtencionReportePorId() {

        Reporte reporte = new Reporte();
        reporte.setIdReporte(1);
        reporte.setTipoReporte("Ventas");
        reporte.setDescripcion("Reporte mensual de ventas");
        reporte.setFechaGeneracion("2026-01-22");
        reporte.setEstado("Generado");

        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporte));

        Reporte resultado = reporteService.obtenerReportePorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdReporte());
        assertEquals("Ventas", resultado.getTipoReporte());
        assertEquals("Reporte mensual de ventas", resultado.getDescripcion());
        assertEquals("2026-01-22", resultado.getFechaGeneracion());
        assertEquals("Generado", resultado.getEstado());

        verify(reporteRepository, times(1)).findById(1);
    }
    
}
