package com.depazsotelo.matricula.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cuota")
public class Cuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codCuota; // PK

    // FK: Sabemos a qué matrícula pertenece
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_matricula", nullable = false)
    private Matricula matricula;

    // FK: Sabemos qué se está cobrando (Matrícula, Pensión Marzo, etc.)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_concepto", nullable = false)
    private Concepto concepto;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal montoCobrado;

    // Estados: "PENDIENTE", "PAGADO", "BLOQUEADO"
    @Column(length = 20, nullable = false)
    private String estado;

    private LocalDateTime fechaPago; // Se llena cuando cancelan la cuota

    @Column(length = 30)
    private String recibo; // Ej: "BOL-2026-001" al realizar el pago

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaRegistro; // Auditoría
}