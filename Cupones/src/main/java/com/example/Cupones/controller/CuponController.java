package com.example.Cupones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Cupones.models.entities.Cupon;
import com.example.Cupones.models.request.AgregarCupon;
import com.example.Cupones.service.CuponService;

@RestController
@RequestMapping("/cupones")
public class CuponController extends BaseController { 

    @Autowired
    private CuponService cuponService;

    @GetMapping
    public List<Cupon> listar() {
        return cuponService.listarTodos();
    }
    @GetMapping("/{codigo}")
    public ResponseEntity<Cupon> obtenerPorCodigo(@PathVariable String codigo) {
        return cuponService.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
    @PostMapping
    public Cupon crear(@RequestBody AgregarCupon request) {
        return cuponService.guardar(request);
    }
}
