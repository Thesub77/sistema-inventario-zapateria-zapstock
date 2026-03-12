/*
 * @author Douglas Quiroz (@thesub77)
 * @project_name Zapstock
 */
package com.thesub77.zapstock.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
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
    private Long idRol;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RolPrivilegio> privilegios = new ArrayList<>();

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
    public Rol(String nombreRol, List<RolPrivilegio> privilegios, String descripcionRol, boolean estado) {
        this.nombreRol = nombreRol;
        this.privilegios = privilegios;
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

    public List<RolPrivilegio> getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(List<RolPrivilegio> privilegios) {
        this.privilegios = privilegios;
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
