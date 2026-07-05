package com.depazsotelo.matricula.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tipo_concepto")
public class TipoConcepto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codTipoConcepto; // PK

    @Column(length = 50, unique = true, nullable = false)
    private String nombre; // Ej: Fijo, Mensual, Opcional

    @Column(nullable = false)
    private Boolean estado = true;
}