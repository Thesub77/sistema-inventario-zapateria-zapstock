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
@Table(name = "Reporte")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReporte")
    private Long idReporte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @Column(name = "codigoReporte", unique = true, length = 16, nullable = false)
    private String codigoReporte;

    @Column(name = "tipoArchivo", length = 8, nullable = false)
    private String tipoArchivo;

    @Column(name = "descripcionReporte", length = 128, nullable = false)
    private String descripcionReporte;

    @Column(name = "rutaArchivo", length = 256, nullable = false)
    private String rutaArchivo;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    // Constructor vacio para instancias
    public Reporte() {

    }

    // Constructor completo para entrada manual
    public Reporte(Usuario usuario, String codigoReporte, String tipoArchivo, String descripcionReporte, String rutaArchivo, boolean estado) {
        this.usuario = usuario;
        this.codigoReporte = codigoReporte;
        this.tipoArchivo = tipoArchivo;
        this.descripcionReporte = descripcionReporte;
        this.rutaArchivo = rutaArchivo;
        this.estado = estado;
    }

    // Getters y Setters para interaccion con los atributos
    public Long getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Long idReporte) {
        this.idReporte = idReporte;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCodigoReporte() {
        return codigoReporte;
    }

    public void setCodigoReporte(String codigoReporte) {
        this.codigoReporte = codigoReporte;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public String getDescripcionReporte() {
        return descripcionReporte;
    }

    public void setDescripcionReporte(String descripcionReporte) {
        this.descripcionReporte = descripcionReporte;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
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
        Reporte reporte = (Reporte) o;
        return Objects.equals(idReporte, reporte.idReporte);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReporte);
    }

}
