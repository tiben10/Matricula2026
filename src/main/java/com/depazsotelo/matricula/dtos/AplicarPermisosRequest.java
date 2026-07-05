package com.depazsotelo.matricula.dtos;

import lombok.Data;
import java.util.List;

// MEJORA: lo que llega cuando el Superusuario presiona el botón "Aplicar"
@Data
public class AplicarPermisosRequest {
    private Integer idRol;
    private List<PermisoItemRequest> permisos;
}