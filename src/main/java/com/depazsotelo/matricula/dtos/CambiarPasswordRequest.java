package com.depazsotelo.matricula.dtos;

import lombok.Data;

@Data
public class CambiarPasswordRequest {
    private String passwordActual;
    private String passwordNueva;
}