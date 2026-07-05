package com.depazsotelo.matricula.repositories;

import com.depazsotelo.matricula.models.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelRepository extends JpaRepository<Nivel, Integer> {}