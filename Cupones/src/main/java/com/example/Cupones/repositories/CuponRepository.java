package com.example.Cupones.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Cupones.models.entities.Cupon;

public interface CuponRepository extends JpaRepository<Cupon , Long>  {
    Optional <Cupon> findByCodigo (String codigo);

    
}
