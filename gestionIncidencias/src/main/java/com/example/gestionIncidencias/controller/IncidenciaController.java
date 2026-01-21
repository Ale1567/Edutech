package com.example.gestionIncidencias.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestionIncidencias.models.entities.Incidencia;
import com.example.gestionIncidencias.models.request.ActualizarIncidencia;
import com.example.gestionIncidencias.models.request.AgregarIncidencia;
import com.example.gestionIncidencias.services.IncidenciaService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RequestMapping("incidencia")
@RestController
public class IncidenciaController {
    
    @Autowired
    private IncidenciaService incidenciaService;

    @GetMapping("")
    public List<Incidencia> obtenerTodo(){
        return incidenciaService.obtenerIncidencias();
    }

     @GetMapping("/{incidenciaid}")
    public EntityModel<Incidencia> obtenerPorId(@PathVariable int idIncidencia) {
        Incidencia incidencia = incidenciaService.obtenerIncidenciasPorId(idIncidencia);

        Link deleteLink = linkTo(IncidenciaController.class).slash(idIncidencia).withRel("Eliminar incidencia");
        Link selfLink = linkTo(methodOn(IncidenciaController.class).obtenerTodo()).withRel("Obtener todas las incidencias");
   
        return EntityModel.of(incidencia,selfLink,deleteLink);
    }

    @PostMapping("")
    public Incidencia agregarIncidencia(@RequestBody AgregarIncidencia nueva) {            
        return incidenciaService.agregarIncidencia(nueva);
    }
    

    @PutMapping("")
    public Incidencia actualizarIncidencia(@RequestBody ActualizarIncidencia nueva ) {
        return incidenciaService.actualizarIncidencia(nueva);
    }

    @DeleteMapping("/{idIncidencia}")
    public String eliminarIncidencia(@PathVariable int idIncidencia){
        return incidenciaService.eliminarIncidencia(idIncidencia);
    }
}
