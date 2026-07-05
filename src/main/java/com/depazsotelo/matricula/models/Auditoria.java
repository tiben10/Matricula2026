package com.depazsotelo.matricula.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codAuditoria; // PK

    // FK: Referencia al Usuario que hizo la acción
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_usuario", nullable = false)
    private Usuario usuario;

    @Column(length = 50, nullable = false)
    private String modulo; // Ej: Matrícula, Conceptos, Seguridad

    @Column(length = 50, nullable = false)
    private String tablaAfectada;

    @Column(length = 20, nullable = false)
    private String operacion; // Ej: INSERT, UPDATE, DELETE, LOGIN, PAGO

    @Column(nullable = false)
    private Integer codigoRegistro; // PK del registro que fue afectado

    // Usamos TEXT porque aquí guardaremos un JSON con los datos anteriores
    @Column(columnDefinition = "TEXT")
    private String valorAnterior;

    // Usamos TEXT porque aquí guardaremos un JSON con los datos nuevos
    @Column(columnDefinition = "TEXT")
    private String valorNuevo;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaHora; // Fecha y hora automática del servidor BD

    @Column(length = 45, nullable = false)
    private String ipOrigen; // IPv4 o IPv6

    @Column(length = 100)
    private String equipo; // Nombre del equipo (Opcional según rúbrica)

    @Column(length = 150)
    private String navegador; // Chrome, Firefox, Edge, etc. (Opcional)
}