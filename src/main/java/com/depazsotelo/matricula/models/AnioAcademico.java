package com.depazsotelo.matricula.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "anio_academico")
public class AnioAcademico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codAnioAcademico;

    @Column(length = 4, unique = true, nullable = false)
    private String anio; // Ej: "2026"

    @Column(nullable = false)
    private Boolean estado = true;
}