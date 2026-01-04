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
@Table(name = "Proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProveedor")
    private long idProveedor;

    @Column(name = "nombreEmpresa", unique = true, length = 64)
    private String nombreEmpresa;

    @Column(name = "primerNombreRepresentante", length = 16, nullable = false)
    private String primerNombreRepresentante;

    @Column(name = "segundoNombreRepresentante", length = 16)
    private String SegundoNombreRepresentante;

    @Column(name = "primerApellidoRepresentante", length = 16, nullable = false)
    private String primerApellidoRepresentante;

    @Column(name = "segundoApellidoRepresentante", length = 16)
    private String segundoApellidoRepresentante;

    @Column(name = "telefonoContacto", length = 11, nullable = false)
    private String telefonoContacto;

    @Column(name = "correoElectronico", unique = true, length = 64)
    private String correoElectronico;

    @Column(name = "direccion", length = 256)
    private String direccion;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    // Constructor completo para entrada manual
    public Proveedor(String nombreEmpresa, String primerNombreRepresentante, String SegundoNombreRepresentante, String primerApellidoRepresentante, String segundoApellidoRepresentante, String telefonoContacto, String correoElectronico, String direccion, boolean estado) {
        this.nombreEmpresa = nombreEmpresa;
        this.primerNombreRepresentante = primerNombreRepresentante;
        this.SegundoNombreRepresentante = SegundoNombreRepresentante;
        this.primerApellidoRepresentante = primerApellidoRepresentante;
        this.segundoApellidoRepresentante = segundoApellidoRepresentante;
        this.telefonoContacto = telefonoContacto;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        this.estado = estado;
    }

    // Constructor vacio para instancias
    public Proveedor() {
    }

    // Getters y Setters para interaccion con los atributos
    public long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getPrimerNombreRepresentante() {
        return primerNombreRepresentante;
    }

    public void setPrimerNombreRepresentante(String primerNombreRepresentante) {
        this.primerNombreRepresentante = primerNombreRepresentante;
    }

    public String getSegundoNombreRepresentante() {
        return SegundoNombreRepresentante;
    }

    public void setSegundoNombreRepresentante(String SegundoNombreRepresentante) {
        this.SegundoNombreRepresentante = SegundoNombreRepresentante;
    }

    public String getPrimerApellidoRepresentante() {
        return primerApellidoRepresentante;
    }

    public void setPrimerApellidoRepresentante(String primerApellidoRepresentante) {
        this.primerApellidoRepresentante = primerApellidoRepresentante;
    }

    public String getSegundoApellidoRepresentante() {
        return segundoApellidoRepresentante;
    }

    public void setSegundoApellidoRepresentante(String segundoApellidoRepresentante) {
        this.segundoApellidoRepresentante = segundoApellidoRepresentante;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
        Proveedor proveedor = (Proveedor) o;
        return Objects.equals(idProveedor, proveedor.idProveedor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProveedor);
    }

}
