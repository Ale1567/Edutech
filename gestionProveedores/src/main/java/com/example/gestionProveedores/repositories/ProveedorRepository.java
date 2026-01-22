package com.example.gestionProveedores.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestionProveedores.models.entities.Proveedor;

@Repository

public interface ProveedorRepository  extends JpaRepository<Proveedor, Long> {
    
}
