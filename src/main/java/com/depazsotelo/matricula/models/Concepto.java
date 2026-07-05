package com.depazsotelo.matricula.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
// Llave única compuesta: No pueden haber dos "Matrícula" en "2026"
@Table(name = "concepto", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cod_anio_academico", "nombre_concepto"})
})
public class Concepto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codConcepto; // PK [cite: 359]

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cod_anio_academico", nullable = false)
    private AnioAcademico anioAcademico; // FK [cite: 359]

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cod_tipo_concepto", nullable = false)
    private TipoConcepto tipoConcepto; // FK [cite: 359]

    @Column(name = "nombre_concepto", length = 80, nullable = false)
    private String nombreConcepto; // Ej: Matrícula, Marzo [cite: 359]

    // Usamos BigDecimal porque es la mejor práctica en Java para manejar dinero (Numeric 10,2)
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal monto; // [cite: 359]

    @Column(nullable = false)
    private Short ordenPago; // Para saber qué se paga primero [cite: 359]

    @Column(nullable = false)
    private Boolean obligatorio; // [cite: 359]

    // --- MAGIA: OPTIMISTIC LOCK ---
    @Version
    private Integer version; // [cite: 359, 655, 656]

    @Column(nullable = false)
    private Boolean estado = true; // [cite: 361]

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaRegistro; // [cite: 361]
}