package com.depazsotelo.matricula.dtos;

import lombok.Data;

// MEJORA: representa una fila del checkbox de permisos en el frontend
@Data
public class PermisoItemRequest {
    private Integer idFuncionalidad;
    private Boolean ver;
    private Boolean crear;
    private Boolean editar;
    private Boolean eliminar;
    private Boolean imprimir;
}