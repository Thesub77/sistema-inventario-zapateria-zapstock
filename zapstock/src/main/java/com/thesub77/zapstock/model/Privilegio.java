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
@Table(name = "Privilegio")
public class Privilegio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPrivilegio")
    private long idPrivilegio;

    @Column(name = "nombrePrivilegio", unique = true, length = 32, nullable = false)
    private String nombrePrivilegio;

    @Column(name = "descripcionPrivilegio", length = 128)
    private String descripcionPrivilegio;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    // Constructor vacio para instancias
    public Privilegio() {

    }

    // Constructor completo para entrada manual
    public Privilegio(String nombrePrivilegio, String descripcionPrivilegio, boolean estado) {
        this.nombrePrivilegio = nombrePrivilegio;
        this.descripcionPrivilegio = descripcionPrivilegio;
        this.estado = estado;
    }

    // Getters y Setters para interaccion con los atributos
    public long getIdPrivilegio() {
        return idPrivilegio;
    }

    public void setIdPrivilegio(long idPrivilegio) {
        this.idPrivilegio = idPrivilegio;
    }

    public String getNombrePrivilegio() {
        return nombrePrivilegio;
    }

    public void setNombrePrivilegio(String nombrePrivilegio) {
        this.nombrePrivilegio = nombrePrivilegio;
    }

    public String getDescripcionPrivilegio() {
        return descripcionPrivilegio;
    }

    public void setDescripcionPrivilegio(String descripcionPrivilegio) {
        this.descripcionPrivilegio = descripcionPrivilegio;
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
        Privilegio privilegio = (Privilegio) o;
        return Objects.equals(idPrivilegio, privilegio.idPrivilegio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPrivilegio);
    }

}
