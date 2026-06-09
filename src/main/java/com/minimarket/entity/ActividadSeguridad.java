package com.minimarket.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ActividadSeguridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(nullable = false)
    private String tipoEvento;

    @Column(nullable = false)
    private String descripcion;

    private String ipOrigen;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    public ActividadSeguridad() {
    }

    public ActividadSeguridad(String username, String tipoEvento, String descripcion, String ipOrigen) {
        this.username = username;
        this.tipoEvento = tipoEvento;
        this.descripcion = descripcion;
        this.ipOrigen = ipOrigen;
        this.fechaRegistro = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getIpOrigen() {
        return ipOrigen;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
}
