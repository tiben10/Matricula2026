package com.depazsotelo.matricula.repositories;

import com.depazsotelo.matricula.models.AnioAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnioAcademicoRepository extends JpaRepository<AnioAcademico, Integer> {}