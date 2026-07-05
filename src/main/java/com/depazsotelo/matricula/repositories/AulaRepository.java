package com.depazsotelo.matricula.repositories;

import com.depazsotelo.matricula.models.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Integer> {}