/*
 * @author Douglas Quiroz (@thesub77)
 * @project_name Zapstock
 */
package com.thesub77.zapstock.model;

import jakarta.persistence.*;
import java.util.Objects;

/**
 *
 */
@Entity
@Table(name = "RolPrivilegio")
public class RolPrivilegio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRolPrivilegio")
    private Long idRolPrivilegio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPrivilegio", nullable = false)
    private Privilegio privilegio;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    // Constructor vacio para instancias
    public RolPrivilegio() {

    }

    // Constructor completo para entrada manual
    public RolPrivilegio(Usuario usuario, Rol rol, Privilegio privilegio, boolean estado) {
        this.usuario = usuario;
        this.rol = rol;
        this.privilegio = privilegio;
        this.estado = estado;
    }

    // Getters y Setters para interaccion con los atributos
    public Long getIdRolPrivilegio() {
        return idRolPrivilegio;
    }

    public void setIdRolPrivilegio(Long idRolPrivilegio) {
        this.idRolPrivilegio = idRolPrivilegio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Privilegio getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(Privilegio privilegio) {
        this.privilegio = privilegio;
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
        RolPrivilegio rolP = (RolPrivilegio) o;
        return Objects.equals(idRolPrivilegio, rolP.idRolPrivilegio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRolPrivilegio);
    }

}
