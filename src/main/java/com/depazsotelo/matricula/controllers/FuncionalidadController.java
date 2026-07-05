package com.depazsotelo.matricula.controllers;

import com.depazsotelo.matricula.models.Funcionalidad;
import com.depazsotelo.matricula.repositories.FuncionalidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/funcionalidades")
@RequiredArgsConstructor
public class FuncionalidadController {

    private final FuncionalidadRepository funcionalidadRepository;

    // MEJORA: solo trae las raíces; el frontend arma el árbol pidiendo los hijos
    // por cada nodo, o puedes mapear a un DTO recursivo si prefieres un solo request.
    @GetMapping("/raiz")
    public List<Funcionalidad> listarRaiz() {
        return funcionalidadRepository.findByPadreIsNull();
    }

    @GetMapping
    public List<Funcionalidad> listarTodas() {
        return funcionalidadRepository.findAll();
    }

    @PostMapping
    public Funcionalidad crear(@RequestBody Funcionalidad funcionalidad) {
        return funcionalidadRepository.save(funcionalidad);
    }
}