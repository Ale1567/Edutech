package com.example.opiniones.y.cupones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.opiniones.y.cupones.models.entities.Cupon;
import com.example.opiniones.y.cupones.models.request.AgregarCupon;
import com.example.opiniones.y.cupones.service.CuponService;

@RestController
@RequestMapping("cupon")


public class CuponController {
    @Autowired
    private CuponService cuponService;

    @GetMapping("")
    public List<Cupon> listar() {
        return cuponService.obtenerTodos();
    }

    @PostMapping("")
    public Cupon crear(@RequestBody AgregarCupon nuevo) {
        return cuponService.guardar(nuevo);
    }

    @GetMapping("/{codigo}")
    public Cupon obtenerPorCodigo(@PathVariable String codigo) {
        return cuponService.buscarPorCodigo(codigo);
    }

    
}
