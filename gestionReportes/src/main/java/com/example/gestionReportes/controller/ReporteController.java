package com.example.gestionReportes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestionReportes.models.entities.Reporte;
import com.example.gestionReportes.models.request.ActualizarReporte;
import com.example.gestionReportes.models.request.AgregarReporte;
import com.example.gestionReportes.services.ReporteService;

@RestController
@RequestMapping("reporte")
public class ReporteController {

     @Autowired
    private ReporteService reporteService;

    @GetMapping("")
    public List<Reporte> obtenerTodo(){
        return reporteService.obtenerReportes();
    }

    @GetMapping("/{idReporte}")
    public Reporte obtenerPorId(@PathVariable int idReporte) {
        return reporteService.obtenerReportePorId(idReporte); 
    }

    @PostMapping("")
    public Reporte agregarReporte(@RequestBody AgregarReporte nuevo) {
        return reporteService.agregarReporte(nuevo);
    }

    @PutMapping("")
    public Reporte actualizarReporte(@RequestBody ActualizarReporte nuevo) {
        return reporteService.actualizarReporte(nuevo);
    }

    @DeleteMapping("/{idReporte}")
    public String eliminarReporte(@PathVariable int idReporte){
        return reporteService.eliminarReporte(idReporte);
    }
    
}
