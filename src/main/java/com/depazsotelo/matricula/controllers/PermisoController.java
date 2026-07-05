package com.depazsotelo.matricula.controllers;

import com.depazsotelo.matricula.dtos.AplicarPermisosRequest;
import com.depazsotelo.matricula.models.RolFuncionalidad;
import com.depazsotelo.matricula.repositories.RolFuncionalidadRepository;
import com.depazsotelo.matricula.services.PermisoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/permisos")
@RequiredArgsConstructor
public class PermisoController {

    private final PermisoService permisoService;
    private final RolFuncionalidadRepository rolFuncionalidadRepository;

    // MEJORA: trae los permisos actuales de un rol para pintar los checkboxes marcados
    @GetMapping("/rol/{idRol}")
    public List<RolFuncionalidad> obtenerPorRol(@PathVariable Integer idRol) {
        return rolFuncionalidadRepository.findByRolIdRol(idRol);
    }

    // MEJORA: el botón "Aplicar" del spec
    @PostMapping("/aplicar")
    public ResponseEntity<?> aplicar(@RequestBody AplicarPermisosRequest request) {
        try {
            permisoService.aplicarPermisos(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}