/*
 * @author Douglas Quiroz (@thesub77)
 * @project_name Zapstock
 */
package com.thesub77.zapstock.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 */
@Entity
@Table(name = "VarianteProducto")
public class VarianteProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVariante")
    private Long idVariante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    @Column(name = "codigoVariante", unique = true, length = 24, nullable = false)
    private String codigoVariante;

    @Column(name = "talla", length = 8, nullable = false)
    private String talla;

    @Column(name = "color", length = 16, nullable = false)
    private String color;

    @Column(name = "cantidadStock", nullable = false)
    private int cantidadStock;

    @Column(name = "stockMinimo", nullable = false)
    private int stockMinimo;

    @Column(name = "costoCompraDocena", precision = 10, scale = 2, nullable = false)
    private BigDecimal costoCompraDocena;

    @Column(name = "costoAdicionalDocena", precision = 10, scale = 2, nullable = false)
    private BigDecimal costoAdicionalDocena;

    @Column(name = "gananciaMonto", precision = 5, scale = 2, nullable = false)
    private BigDecimal gananciaMonto;

    @Column(name = "precioVentaSugerido", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioVentaSugerido;

    @Column(name = "descuentoVariante", precision = 5, scale = 2, nullable = false)
    private BigDecimal descuentoVariante;

    @Column(name = "fechaIngreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    // Constructor vacio para instancias
    public VarianteProducto() {

    }

    // Constructor completo para entrada manual
    public VarianteProducto(Producto producto, String codigoVariante, String talla, String color, int cantidadStock, int stockMinimo, BigDecimal costoCompraDocena, BigDecimal costoAdicionalDocena, BigDecimal gananciaMonto, BigDecimal precioVentaSugerido, BigDecimal descuentoVariante, LocalDate fechaIngreso, boolean estado) {
        this.producto = producto;
        this.codigoVariante = codigoVariante;
        this.talla = talla;
        this.color = color;
        this.cantidadStock = cantidadStock;
        this.stockMinimo = stockMinimo;
        this.costoCompraDocena = costoCompraDocena;
        this.costoAdicionalDocena = costoAdicionalDocena;
        this.gananciaMonto = gananciaMonto;
        this.precioVentaSugerido = precioVentaSugerido;
        this.descuentoVariante = descuentoVariante;
        this.fechaIngreso = fechaIngreso;
        this.estado = estado;
    }

    // Getters y Setters para interaccion con los atributos
    public long getIdVariante() {
        return idVariante;
    }

    public void setIdVariante(long idVariante) {
        this.idVariante = idVariante;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getCodigoVariante() {
        return codigoVariante;
    }

    public void setCodigoVariante(String codigoVariante) {
        this.codigoVariante = codigoVariante;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public BigDecimal getCostoCompraDocena() {
        return costoCompraDocena;
    }

    public void setCostoCompraDocena(BigDecimal costoCompraDocena) {
        this.costoCompraDocena = costoCompraDocena;
    }

    public BigDecimal getCostoAdicionalDocena() {
        return costoAdicionalDocena;
    }

    public void setCostoAdicionalDocena(BigDecimal costoAdicionalDocena) {
        this.costoAdicionalDocena = costoAdicionalDocena;
    }

    public BigDecimal getGananciaMonto() {
        return gananciaMonto;
    }

    public void setGananciaMonto(BigDecimal gananciaMonto) {
        this.gananciaMonto = gananciaMonto;
    }

    public BigDecimal getPrecioVentaSugerido() {
        return precioVentaSugerido;
    }

    public void setPrecioVentaSugerido(BigDecimal precioVentaSugerido) {
        this.precioVentaSugerido = precioVentaSugerido;
    }

    public BigDecimal getDescuentoVariante() {
        return descuentoVariante;
    }

    public void setDescuentoVariante(BigDecimal descuentoVariante) {
        this.descuentoVariante = descuentoVariante;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
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
        VarianteProducto variante = (VarianteProducto) o;
        return Objects.equals(idVariante, variante.idVariante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVariante);
    }

}
