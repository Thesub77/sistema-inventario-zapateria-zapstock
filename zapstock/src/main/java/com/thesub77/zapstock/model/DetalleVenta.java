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
@Table(name = "DetalleVenta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetalleVenta")
    private Long idDetalleVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idVenta", nullable = false)
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idVariante", nullable = false)
    private VarianteProducto VarianteProductoVenta;

    @Column(name = "cantidadVenta", nullable = false)
    private int cantidadVenta;

    @Column(name = "precioVentaPar", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioVentaPar;

    @Column(name = "descuentoCalzadoVenta", precision = 5, scale = 2, nullable = false)
    private BigDecimal descuentoCalzadoVenta;

    @Column(name = "subtotalCalzadoVenta", precision = 10, scale = 2, nullable = false)
    private BigDecimal subtotalCalzadoVenta;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    // Constructor vacio para instancias
    public DetalleVenta() {

    }

    // Constructor completo para entrada manual
    public DetalleVenta(Venta venta, VarianteProducto VarianteProductoVenta, int cantidadVenta, BigDecimal precioVentaPar, BigDecimal descuentoCalzadoVenta, BigDecimal subtotalCalzadoVenta, boolean estado) {
        this.venta = venta;
        this.VarianteProductoVenta = VarianteProductoVenta;
        this.cantidadVenta = cantidadVenta;
        this.precioVentaPar = precioVentaPar;
        this.descuentoCalzadoVenta = descuentoCalzadoVenta;
        this.subtotalCalzadoVenta = subtotalCalzadoVenta;
        this.estado = estado;
    }

    // Getters y Setters para interaccion con los atributos
    public Long getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(Long idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public VarianteProducto getVarianteProductoVenta() {
        return VarianteProductoVenta;
    }

    public void setVarianteProductoVenta(VarianteProducto VarianteProductoVenta) {
        this.VarianteProductoVenta = VarianteProductoVenta;
    }

    public int getCantidadVenta() {
        return cantidadVenta;
    }

    public void setCantidadVenta(int cantidadVenta) {
        this.cantidadVenta = cantidadVenta;
    }

    public BigDecimal getPrecioVentaPar() {
        return precioVentaPar;
    }

    public void setPrecioVentaPar(BigDecimal precioVentaPar) {
        this.precioVentaPar = precioVentaPar;
    }

    public BigDecimal getDescuentoCalzadoVenta() {
        return descuentoCalzadoVenta;
    }

    public void setDescuentoCalzadoVenta(BigDecimal descuentoCalzadoVenta) {
        this.descuentoCalzadoVenta = descuentoCalzadoVenta;
    }

    public BigDecimal getSubtotalCalzadoVenta() {
        return subtotalCalzadoVenta;
    }

    public void setSubtotalCalzadoVenta(BigDecimal subtotalCalzadoVenta) {
        this.subtotalCalzadoVenta = subtotalCalzadoVenta;
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
        DetalleVenta detalleVenta = (DetalleVenta) o;
        return Objects.equals(idDetalleVenta, detalleVenta.idDetalleVenta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDetalleVenta);
    }

}
