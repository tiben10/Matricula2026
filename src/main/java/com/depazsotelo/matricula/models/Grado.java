package com.depazsotelo.matricula.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "grado")
public class Grado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codGrado;

    @Column(length = 50, unique = true, nullable = false)
    private String nombre; // Ej: "1°", "3 años"

    @Column(nullable = false)
    private Boolean estado = true;
}