package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Offre;

public interface OffreRepo extends JpaRepository<Offre, Integer>{
}
