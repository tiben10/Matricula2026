package com.depazsotelo.matricula.dtos;

import lombok.Data;

@Data
public class AlumnoRequest {
    private Integer codTipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fechaNacimiento;
}