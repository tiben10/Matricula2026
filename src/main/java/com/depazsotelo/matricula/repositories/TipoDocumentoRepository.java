package com.depazsotelo.matricula.repositories;

import com.depazsotelo.matricula.models.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {}