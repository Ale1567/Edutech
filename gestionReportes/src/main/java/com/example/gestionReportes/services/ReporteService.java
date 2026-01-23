package com.example.gestionReportes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestionReportes.models.entities.Reporte;
import com.example.gestionReportes.models.request.ActualizarReporte;
import com.example.gestionReportes.models.request.AgregarReporte;
import com.example.gestionReportes.repositories.ReporteRepository;

@Service
public class ReporteService {
    
    @Autowired
    private ReporteRepository reporteRepository;

    public List<Reporte> obtenerReportes(){
        return reporteRepository.findAll();
    }

    public Reporte obtenerReportePorId(int reporteId){
        Reporte reporte = reporteRepository.findById(reporteId).orElse(null);
        if (reporte == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reporte no encontrado");
        }
        return reporte;
    }

    public Reporte agregarReporte(AgregarReporte nuevo){
        Reporte reporte = new Reporte();
        reporte.setTipoReporte(nuevo.getTipoReporte());
        reporte.setDescripcion(nuevo.getDescripcion());
        reporte.setFechaGeneracion(nuevo.getFechaGeneracion());
        reporte.setEstado(nuevo.getEstado());

        return reporteRepository.save(reporte);
    }

    public Reporte actualizarReporte(ActualizarReporte nuevo){
        Reporte reporte = reporteRepository.findById(nuevo.getIdReporte()).orElse(null);
        if (reporte == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reporte no encontrado");
        }
        reporte.setTipoReporte(nuevo.getTipoReporte());
        reporte.setDescripcion(nuevo.getDescripcion());
        reporte.setFechaGeneracion(nuevo.getFechaGeneracion());
        reporte.setEstado(nuevo.getEstado());

        return reporteRepository.save(reporte);
    }

    public String eliminarReporte(int reporteId){
        if (reporteRepository.existsById(reporteId)) {
            reporteRepository.deleteById(reporteId);
            return "Reporte eliminado";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reporte no encontrado");
        }
    }
    
}
