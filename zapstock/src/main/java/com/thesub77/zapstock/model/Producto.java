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
@Table(name = "Producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProducto")
    private Long idProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCategoria", nullable = false)
    private Categoria categoria;

    @Column(name = "codigoProducto", unique = true, length = 24, nullable = false)
    private String codigoProducto;

    @Column(name = "nombreCalzado", length = 64, nullable = false)
    private String nombreCalzado;

    @Column(name = "descripcion", length = 128)
    private String descripcion;

    @Column(name = "marca", length = 32, nullable = false)
    private String marca;

    @Column(name = "materialPrincipal", length = 32, nullable = false)
    private String materialPrincipal;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    // Constructor vacio para instancias
    public Producto() {

    }

    // Constructor completo para entrada manual
    public Producto(Categoria categoria, String codigoProducto, String nombreCalzado, String descripcion, String marca, String materialPrincipal, boolean estado) {
        this.categoria = categoria;
        this.codigoProducto = codigoProducto;
        this.nombreCalzado = nombreCalzado;
        this.descripcion = descripcion;
        this.marca = marca;
        this.materialPrincipal = materialPrincipal;
        this.estado = estado;
    }

    // Getters y Setters para interaccion con los atributos
    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreCalzado() {
        return nombreCalzado;
    }

    public void setNombreCalzado(String nombreCalzado) {
        this.nombreCalzado = nombreCalzado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMaterialPrincipal() {
        return materialPrincipal;
    }

    public void setMaterialPrincipal(String materialPrincipal) {
        this.materialPrincipal = materialPrincipal;
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
        Producto producto = (Producto) o;
        return Objects.equals(idProducto, producto.idProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto);
    }

}
