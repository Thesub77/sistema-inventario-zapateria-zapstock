/*
 * @author Douglas Quiroz (@thesub77)
 * @project_name Zapstock
 */
package com.thesub77.zapstock.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
@Entity
@Table(name = "OrdenCompra")
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOrdenCompra")
    private Long idOrdenCompra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProveedor", nullable = false)
    private Proveedor proveedor;

    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleOrdenCompra> detalles = new ArrayList<>();

    @Column(name = "codigoOrden", unique = true, length = 24, nullable = false)
    private String codigoOrden;

    @Column(name = "fechaOrden", nullable = false)
    private LocalDate fechaOrden;

    @Column(name = "subtotalOrden", precision = 10, scale = 2, nullable = false)
    private BigDecimal subtotalOrden;

    @Column(name = "descuentoCompra", precision = 5, scale = 2, nullable = false)
    private BigDecimal descuentoCompra;

    @Column(name = "precioTotal", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioTotal;

    @Column(name = "estado")
    private boolean estado;

    // Constructor vacio para instancias
    public OrdenCompra() {

    }

    // Constructor completo para entrada manual
    public OrdenCompra(Proveedor proveedor, List<DetalleOrdenCompra> detalles, String codigoOrden, LocalDate fechaOrden, BigDecimal subtotalOrden, BigDecimal descuentoCompra, BigDecimal precioTotal, boolean estado) {
        this.proveedor = proveedor;
        this.detalles = detalles;
        this.codigoOrden = codigoOrden;
        this.fechaOrden = fechaOrden;
        this.subtotalOrden = subtotalOrden;
        this.descuentoCompra = descuentoCompra;
        this.precioTotal = precioTotal;
        this.estado = estado;
    }

    public long getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(long idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<DetalleOrdenCompra> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrdenCompra> detalles) {
        this.detalles = detalles;
    }

    public String getCodigoOrden() {
        return codigoOrden;
    }

    public void setCodigoOrden(String codigoOrden) {
        this.codigoOrden = codigoOrden;
    }

    public LocalDate getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(LocalDate fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public BigDecimal getSubtotalOrden() {
        return subtotalOrden;
    }

    public void setSubtotalOrden(BigDecimal subtotalOrden) {
        this.subtotalOrden = subtotalOrden;
    }

    public BigDecimal getDescuentoCompra() {
        return descuentoCompra;
    }

    public void setDescuentoCompra(BigDecimal descuentoCompra) {
        this.descuentoCompra = descuentoCompra;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
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
        OrdenCompra oc = (OrdenCompra) o;
        return Objects.equals(idOrdenCompra, oc.idOrdenCompra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrdenCompra);
    }

}
