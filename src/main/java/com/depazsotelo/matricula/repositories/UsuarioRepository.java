package com.depazsotelo.matricula.repositories;

import com.depazsotelo.matricula.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Con solo nombrar el método así, Spring Boot arma el "SELECT * FROM usuario WHERE usuario = ?"
    Optional<Usuario> findByUsuario(String usuario);
}