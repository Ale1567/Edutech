package com.example.gestionIncidencias.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestionIncidencias.models.entities.Incidencia;
import com.example.gestionIncidencias.models.request.ActualizarIncidencia;
import com.example.gestionIncidencias.models.request.AgregarIncidencia;
import com.example.gestionIncidencias.repositories.IncidenciaRepository;

@Service
public class IncidenciaService {
    
    @Autowired
    private IncidenciaRepository incidenciaRepository;

    public List<Incidencia> obtenerIncidencias(){
        return incidenciaRepository.findAll();
    }

    public Incidencia obtenerIncidenciasPorId(int incidenciaId){
        Incidencia incidencia = incidenciaRepository.findById(incidenciaId).orElse(null);
        if (incidencia == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidencia no encontrada");
        }
        return incidencia;
    }

    public Incidencia agregarIncidencia(AgregarIncidencia nuevo){
        Incidencia incidencia = new Incidencia();
        incidencia.setDescripcion(nuevo.getDescripcion());
        incidencia.setEstado(nuevo.getEstado());
        incidencia.setPrioridad(nuevo.getPrioridad());
        return incidenciaRepository.save(incidencia);
    }

    public Incidencia actualizarIncidencia(ActualizarIncidencia nuevo){
        Incidencia incidencia = incidenciaRepository.findById(nuevo.getId_incidencia()).orElse(null);
        if (incidencia == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidencia no encontrada");
        }
        incidencia.setDescripcion(nuevo.getDescripcion());
        incidencia.setEstado(nuevo.getEstado());
        incidencia.setPrioridad(nuevo.getId_incidencia());
        return incidenciaRepository.save(incidencia);
    }

    public String eliminarIncidencia(int incidenciaId){
        if (incidenciaRepository.existsById(incidenciaId)) {
            incidenciaRepository.deleteById(incidenciaId);
            return "Incidencia eliminada";
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidencia no encontrada");
        }
    }

    
}
