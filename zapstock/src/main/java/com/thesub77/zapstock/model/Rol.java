/*
 * @author Douglas Quiroz (@thesub77)
 * @proyect_name Zapstock
 */
package com.thesub77.zapstock.model;

import jakarta.persistence.*;
import java.util.Objects;

/**
 *
 */
@Entity
@Table(name = "Rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRol")
    private long idRol;

    @Column(name = "nombreRol", unique = true, length = 16, nullable = false)
    private String nombreRol;

    @Column(name = "descripcionRol", length = 64)
    private String descripcionRol;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    // Constructor vacio para instancias
    public Rol() {

    }

    // Constructor completo para entrada manual
    public Rol(String nombreRol, String descripcionRol, boolean estado) {
        this.nombreRol = nombreRol;
        this.descripcionRol = descripcionRol;
        this.estado = estado;
    }

    // Getters y Setters para interaccion con los atributos
    public long getIdRol() {
        return idRol;
    }

    public void setIdRol(long idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcionRol() {
        return descripcionRol;
    }

    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    // Para comparaciones logicas y caching
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rol rol = (Rol) o;
        return Objects.equals(idRol, rol.idRol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRol);
    }

}
