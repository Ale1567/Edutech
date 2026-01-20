package com.example.opiniones.y.cupones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.opiniones.y.cupones.models.entities.Opinion;
import com.example.opiniones.y.cupones.models.request.AgregarOpinion;
import com.example.opiniones.y.cupones.service.OpinionService;

@RestController
@RequestMapping("opinion")

//hola
public class OpinionController {
    @Autowired
    private OpinionService opinionService;

    @GetMapping("")
    public List<Opinion> listar() {
        return opinionService.obtenerTodas();
    }

    @PostMapping("")
    public Opinion crear(@RequestBody AgregarOpinion nueva) {
        return opinionService.guardar(nueva);
    }
    
}
