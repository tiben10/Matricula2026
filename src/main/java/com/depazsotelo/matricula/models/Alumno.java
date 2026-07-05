package com.depazsotelo.matricula.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "alumno")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codAlumno; // PK

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cod_tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento; // FK

    // --- MAGIA CRIPTOGRÁFICA: Cifrado AES automático ---
    @Convert(converter = com.depazsotelo.matricula.security.AesEncryptor.class)
    @Column(length = 255, unique = true, nullable = false)
    private String numeroDocumento;

    @Column(length = 80, nullable = false)
    private String nombres;

    @Column(length = 60, nullable = false)
    private String apellidoPaterno;

    @Column(length = 60, nullable = false)
    private String apellidoMaterno;

    // --- MAGIA CRIPTOGRÁFICA: Cifrado AES automático ---
    @Convert(converter = com.depazsotelo.matricula.security.AesEncryptor.class)
    @Column(columnDefinition = "TEXT", nullable = false)
    private String fechaNacimiento;

    @Column(nullable = false)
    private Boolean estado = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaRegistro; // Auditoría
}