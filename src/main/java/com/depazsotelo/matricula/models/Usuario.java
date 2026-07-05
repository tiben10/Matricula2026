package com.depazsotelo.matricula.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario; // PK

    @Column(length = 30, unique = true, nullable = false)
    private String usuario; // UK

    @Column(length = 255, nullable = false)
    private String password;

    // Relación: Muchos usuarios pertenecen a un Rol
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol; // FK

    @Column(nullable = false)
    private Boolean estado = true;

    // Campos de Auditoría
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaRegistro;

    // MEJORA: guarda el secreto TOTP de Google Authenticator del usuario.
    // Null hasta que el usuario active el 2FA.
    @Column(length = 100)
    private String secret2FA;

    @UpdateTimestamp
    private LocalDateTime fechaModificacion;

    // Relación recursiva: Un usuario es creado por otro usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_creacion_id")
    private Usuario usuarioCreacion; // FK Usuario
}