package com.example.opiniones.y.cupones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.opiniones.y.cupones.models.entities.Opinion;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Integer> {
}