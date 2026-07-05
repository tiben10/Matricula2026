package com.depazsotelo.matricula.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "nivel")
public class Nivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codNivel;

    @Column(length = 50, unique = true, nullable = false)
    private String nombre; // Ej: "Inicial", "Primaria", "Secundaria"

    @Column(nullable = false)
    private Boolean estado = true;
}