/*
 * @author Douglas Quiroz (@thesub77)
 * @project_name Zapstock
 */
package com.thesub77.zapstock.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 */
@Entity
@Table(name = "DetalleOrdenCompra")
public class DetalleOrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetalleOrden")
    private Long idDetalleOrden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idOrdenCompra", nullable = false)
    private OrdenCompra ordenCompra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idVariante", nullable = false)
    private VarianteProducto varianteProducto;

    @Column(name = "precioDocena", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioDocena;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "descuentoCalzado", precision = 5, scale = 2, nullable = false)
    private BigDecimal descuentoCalzado;

    @Column(name = "subtotalCalzado", precision = 10, scale = 2, nullable = false)
    private BigDecimal subtotalCalzado;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    // Constructor vacio para instancias
    public DetalleOrdenCompra() {

    }

    // Constructor completo para entrada manual
    public DetalleOrdenCompra(OrdenCompra ordenCompra, VarianteProducto varianteProducto, BigDecimal precioDocena, int cantidad, BigDecimal descuentoCalzado, BigDecimal subtotalCalzado, boolean estado) {
        this.ordenCompra = ordenCompra;
        this.varianteProducto = varianteProducto;
        this.precioDocena = precioDocena;
        this.cantidad = cantidad;
        this.descuentoCalzado = descuentoCalzado;
        this.subtotalCalzado = subtotalCalzado;
        this.estado = estado;
    }

    // Getters y Setters para interaccion con los atributos
    public long getIdDetalleOrden() {
        return idDetalleOrden;
    }

    public void setIdDetalleOrden(long idDetalleOrden) {
        this.idDetalleOrden = idDetalleOrden;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public VarianteProducto getVarianteProducto() {
        return varianteProducto;
    }

    public void setVarianteProducto(VarianteProducto varianteProducto) {
        this.varianteProducto = varianteProducto;
    }

    public BigDecimal getPrecioDocena() {
        return precioDocena;
    }

    public void setPrecioDocena(BigDecimal precioDocena) {
        this.precioDocena = precioDocena;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getDescuentoCalzado() {
        return descuentoCalzado;
    }

    public void setDescuentoCalzado(BigDecimal descuentoCalzado) {
        this.descuentoCalzado = descuentoCalzado;
    }

    public BigDecimal getSubtotalCalzado() {
        return subtotalCalzado;
    }

    public void setSubtotalCalzado(BigDecimal subtotalCalzado) {
        this.subtotalCalzado = subtotalCalzado;
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
        DetalleOrdenCompra detalleOrden = (DetalleOrdenCompra) o;
        return Objects.equals(idDetalleOrden, detalleOrden.idDetalleOrden);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDetalleOrden);
    }

}
