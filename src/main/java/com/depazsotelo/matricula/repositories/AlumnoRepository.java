package com.depazsotelo.matricula.repositories;

import com.depazsotelo.matricula.models.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {

    // Gracias al AttributeConverter que hicimos, aquí le pasas el DNI normal,
    // Spring lo encripta en AES por debajo y lo busca en la base de datos. ¡Magia pura!
    Optional<Alumno> findByNumeroDocumento(String numeroDocumento);
}