/*
 * @author Douglas Quiroz (@thesub77)
 * @project_name Zapstock
 */
package com.thesub77.zapstock.repository;

import com.thesub77.zapstock.model.Venta;
import com.thesub77.zapstock.util.DatabaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import java.util.List;

/**
 *
 */
public class VentaRepository {

    public void guardarVenta(Venta venta) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(venta);
            em.getTransaction().commit();
            System.out.println("Se ha registrado la venta: " + venta.getCodigoFactura());

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al registrar venta: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

    public void actualizarVenta(Venta ventaActualizado) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            // Verificar existencia y estado antes de actualizar
            Venta verificarExistencia = em.find(Venta.class, ventaActualizado.getIdVenta());
            if (verificarExistencia == null || !verificarExistencia.isEstado()) {
                throw new EntityNotFoundException("Error al actualizar: Factura no encontrado");
            }

            em.merge(ventaActualizado);
            em.getTransaction().commit();
            System.out.println("Se ha actualizado la factura: " + ventaActualizado.getCodigoFactura());

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al actualizar factura: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

    public Venta buscarVentaPorId(Long idVenta) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            Venta venta = em.find(Venta.class, idVenta);

            if (venta == null || !venta.isEstado()) {
                throw new EntityNotFoundException("Factura con ID " + idVenta + " no encontrado");
            }

            return venta;
        } finally {
            em.close();
        }

    }

    public Venta buscarVentaPorCodigo(String codigoVenta) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            return em.createQuery("SELECT v FROM Venta v WHERE v.codigoFactura = :codigoVenta AND v.estado = true", Venta.class)
                    .setParameter("codigoVenta", codigoVenta)
                    .getSingleResult();

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("Factura " + codigoVenta + " no encontrada " + nre.getMessage() + " " + nre);
        } finally {
            em.close();
        }

    }

    public List<Venta> listarVentas() {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            return em.createQuery("SELECT v FROM Venta v WHERE v.estado = true", Venta.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void borrarVenta(String codigoVenta) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Venta venta = em.createQuery("SELECT v FROM Venta v WHERE v.codigoFactura = :codigoVenta AND r.estado = true", Venta.class)
                    .setParameter("codigoVenta", codigoVenta)
                    .getSingleResult();

            venta.setEstado(false);

            em.getTransaction().commit();

            System.out.println("Se ha eliminado la factura: " + codigoVenta);

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("No se ha encontrado la factura " + nre.getMessage(), nre);
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

        } finally {
            em.close();
        }

    }

}
