package com.depazsotelo.matricula.repositories;

import com.depazsotelo.matricula.models.RolFuncionalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RolFuncionalidadRepository extends JpaRepository<RolFuncionalidad, Integer> {
    // MEJORA: trae todos los permisos configurados para un rol (para pintar los checkboxes)
    List<RolFuncionalidad> findByRolIdRol(Integer idRol);

    // MEJORA: busca si ya existe un permiso para ese rol+funcionalidad (para no duplicar al "Aplicar")
    Optional<RolFuncionalidad> findByRolIdRolAndFuncionalidadIdFuncionalidad(Integer idRol, Integer idFuncionalidad);
}