package com.depazsotelo.matricula.repositories;

import com.depazsotelo.matricula.models.Funcionalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionalidadRepository extends JpaRepository<Funcionalidad, Integer> {
    // MEJORA: trae las funcionalidades raíz (sin padre) para armar el Tree JS desde el nivel superior
    List<Funcionalidad> findByPadreIsNull();
}
