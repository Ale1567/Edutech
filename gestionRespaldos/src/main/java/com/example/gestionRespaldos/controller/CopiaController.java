package com.example.gestionRespaldos.controller;

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

import com.example.gestionRespaldos.models.entities.Copia;
import com.example.gestionRespaldos.models.request.ActualizarCopia;
import com.example.gestionRespaldos.models.request.AgregarCopia;
import com.example.gestionRespaldos.services.CopiaService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RequestMapping("copia")
@RestController
public class CopiaController {
    @Autowired
    private CopiaService copiaService;

    @GetMapping("")
    public List<Copia> obtenerTodo() {
        return copiaService.obtenerTodasLasCopias();
    }

    @GetMapping("/{idCopia}")
    public EntityModel<Copia> obtenerPorId(@PathVariable int idCopia) {
        Copia copia = copiaService.obtenerCopiaPorId(idCopia);

        Link deleteLink = linkTo(CopiaController.class).slash(idCopia).withRel("Eliminar copias");
        Link selfLink = linkTo(methodOn(CopiaController.class).obtenerTodo()).withRel("Obtener todas las copias");
        return EntityModel.of(copia,selfLink,deleteLink);
    }

    @PostMapping("")
    public Copia agregarCopia(@RequestBody AgregarCopia nueva) {            
        return copiaService.agregarCopia(nueva);
    }
    

    @PutMapping("")
    public Copia actualizarCopia(@RequestBody ActualizarCopia nueva ) {
        return copiaService.actualizarCopia(nueva);
    }

    @DeleteMapping("/{idCopia}")
    public String eliminarMarca(@PathVariable int idCopia){
        return copiaService.eliminarCopia(idCopia);
    }
}
