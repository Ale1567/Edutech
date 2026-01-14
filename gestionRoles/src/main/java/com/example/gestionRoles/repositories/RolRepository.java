package com.example.gestionRoles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestionRoles.models.entities.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    
}
