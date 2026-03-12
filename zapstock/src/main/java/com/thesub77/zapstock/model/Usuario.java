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
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Long idUsuario;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RolPrivilegio> rolprivilegio = new ArrayList<>();

    @Column(name = "codigoUsuario", unique = true, length = 16, nullable = false)
    private String codigoUsuario;

    @Column(name = "nombreUsuario", length = 16, nullable = false)
    private String nombreUsuario;

    @Column(name = "apellidoUsuario", length = 16, nullable = false)
    private String apellidoUsuario;

    @Column(name = "correoElectronico", unique = true, length = 64, nullable = false)
    private String correoElectronico;

    @Column(name = "contrasena", length = 64, nullable = false)
    private String contrasena;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    // Constructor vacio para instancias
    public Usuario() {

    }

    // Constructor completo para entrada manual
    public Usuario(String codigoUsuario, String nombreUsuario, String apellidoUsuario, String correoElectronico, String contrasena, boolean estado) {
        this.codigoUsuario = codigoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.estado = estado;
    }

    // Getters y Setters para interaccion con los atributos
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<RolPrivilegio> getRolprivilegio() {
        return rolprivilegio;
    }

    public void setRolprivilegio(List<RolPrivilegio> rolprivilegio) {
        this.rolprivilegio = rolprivilegio;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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
        Usuario usuario = (Usuario) o;
        return Objects.equals(idUsuario, usuario.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario);
    }

}
