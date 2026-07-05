package com.depazsotelo.matricula.dtos;

import lombok.Data;

@Data
public class CrearUsuarioRequest {
    private String usuario;
    private String password;
    private Integer idRol;
}
