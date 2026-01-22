package com.example.gestionRespaldos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestionRespaldos.models.entities.Copia;
import com.example.gestionRespaldos.models.request.ActualizarCopia;
import com.example.gestionRespaldos.models.request.AgregarCopia;
import com.example.gestionRespaldos.repositories.CopiaRepository;

@Service
public class CopiaService {
    @Autowired
    private CopiaRepository copiaRepository;

    public List<Copia> obtenerTodasLasCopias(){
        return copiaRepository.findAll();
    }


    public Copia obtenerCopiaPorId(int copiaId){
        Copia copia = copiaRepository.findById(copiaId).orElse(null);
        if (copia == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Copia no encontrada.");
        }
        return copia;
    }

    public Copia agregarCopia(AgregarCopia nuevo){
        Copia copia = new Copia();
        copia.setVersion(nuevo.getVersion());
        copia.setNombre(nuevo.getNombre());
        return copiaRepository.save(copia);
    }

    public Copia actualizarCopia(ActualizarCopia nuevo){
        Copia copia = copiaRepository.findById(nuevo.getIdCopia()).orElse(null);
        if (copia == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Copia no encontrada.");
        }else{
            copia.setVersion(nuevo.getVersion());
            copia.setNombre(nuevo.getNombre());
            return copiaRepository.save(copia);
        }
    }


    public String eliminarCopia(int copiaId){
        if (copiaRepository.existsById(copiaId)) {
            copiaRepository.deleteById(copiaId);
            return "Copia eliminda correctamente.";
        }else{
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Copia no encontrada.");
        }
    }
    
}
