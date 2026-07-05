package com.depazsotelo.matricula.repositories;

import com.depazsotelo.matricula.models.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Integer> {}