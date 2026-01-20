package com.example.opiniones.y.cupones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.opiniones.y.cupones.models.entities.Cupon;
import com.example.opiniones.y.cupones.models.request.AgregarCupon;
import com.example.opiniones.y.cupones.repository.CuponRepository;
import java.util.List;

@Service
public class CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    public List<Cupon> obtenerTodos() {
        return cuponRepository.findAll();
    }

    public Cupon guardar(AgregarCupon request) {
        Cupon cupon = new Cupon();
        cupon.setCodigo(request.getCodigo());
        cupon.setPorcentaje_descuento(request.getPorcentaje_descuento());
        cupon.setActivo(true); // Por defecto lo creamos activo
        return cuponRepository.save(cupon);
    }

    public Cupon buscarPorCodigo(String codigo) {
        return cuponRepository.findByCodigo(codigo);
    }
}