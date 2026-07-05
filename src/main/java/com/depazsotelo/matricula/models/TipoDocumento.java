package com.depazsotelo.matricula.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tipo_documento")
public class TipoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codTipoDocumento; // PK

    @Column(length = 20, unique = true, nullable = false)
    private String nombre; // Ej: DNI, CE

    @Column(nullable = false)
    private Boolean estado = true;
}