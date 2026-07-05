package com.depazsotelo.matricula.repositories;

import com.depazsotelo.matricula.models.Grado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradoRepository extends JpaRepository<Grado, Integer> {}