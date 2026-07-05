package com.depazsotelo.matricula.models;

import jakarta.persistence.*;
import lombok.Data;

@Data // Lombok nos genera los Getters y Setters automáticamente
@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRol; // PK

    @Column(length = 40, unique = true, nullable = false)
    private String nombreRol; // UK

    @Column(nullable = false)
    private Boolean estado = true; // Para la eliminación lógica
}