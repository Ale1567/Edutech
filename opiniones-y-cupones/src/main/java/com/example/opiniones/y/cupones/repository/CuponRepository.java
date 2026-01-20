package com.example.opiniones.y.cupones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.opiniones.y.cupones.models.entities.Cupon;

@Repository

public interface CuponRepository extends JpaRepository<Cupon, Integer> {
    // busca codigo de cupon
    Cupon findByCodigo(String codigo);
}