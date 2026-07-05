package com.depazsotelo.matricula.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
// ¡Aquí está la magia de la Unique Key Compuesta!
@Table(name = "aula", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cod_anio_academico", "cod_nivel", "cod_grado", "seccion"})
})
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codAula; // PK

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cod_anio_academico", nullable = false)
    private AnioAcademico anioAcademico; // FK

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cod_nivel", nullable = false)
    private Nivel nivel; // FK

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cod_grado", nullable = false)
    private Grado grado; // FK

    @Column(length = 2, nullable = false)
    private String seccion; // Ej: "A", "B"

    @Column(nullable = false)
    private Short capacidadMaxima; // Ej: 35 alumnos

    @Column(nullable = false)
    private Boolean estado = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaRegistro; // Auditoría
}