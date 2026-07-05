package com.depazsotelo.matricula.repositories;

import com.depazsotelo.matricula.models.TipoConcepto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoConceptoRepository extends JpaRepository<TipoConcepto, Integer> {}