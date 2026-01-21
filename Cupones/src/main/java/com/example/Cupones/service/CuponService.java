package com.example.Cupones.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Cupones.models.entities.Cupon;
import com.example.Cupones.models.request.AgregarCupon;
import com.example.Cupones.repositories.CuponRepository;
import java.util.Optional;

@Service
public class CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    public List<Cupon> listarTodos() {
        return cuponRepository.findAll();
    }

    public Optional <Cupon> buscarPorCodigo(String codigo) {
        return cuponRepository.findByCodigo(codigo);
    }

    public Cupon guardar(AgregarCupon  request) {
        Cupon cupon = new Cupon();
        cupon.setCodigo(request.getCodigo());
        cupon.setPorcentajeDescuento(request.getPorcentajeDescuento());
        cupon.setActivo(true);
        return cuponRepository.save(cupon);
    }
}