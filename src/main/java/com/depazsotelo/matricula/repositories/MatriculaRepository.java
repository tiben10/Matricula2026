package com.depazsotelo.matricula.repositories;

import com.depazsotelo.matricula.models.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {

    // Spring Boot lee el nombre larguísimo y entiende: "Busca si existe una matrícula con este alumno y este año"
    boolean existsByAlumnoCodAlumnoAndAnioAcademicoCodAnioAcademico(Integer codAlumno, Integer codAnioAcademico);
}