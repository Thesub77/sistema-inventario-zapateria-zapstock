/*
 * @author Douglas Quiroz (@thesub77)
 * @project_name Zapstock
 */
package com.thesub77.zapstock.repository;

import com.thesub77.zapstock.model.DetalleVenta;
import com.thesub77.zapstock.model.Venta;
import com.thesub77.zapstock.util.DatabaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import java.util.List;

/**
 *
 */
public class DetalleVentaRepository {

    public void guardarDetalleVenta(DetalleVenta detalleVenta) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(detalleVenta);
            em.getTransaction().commit();
            System.out.println("Se ha guardado el registro con exito");
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al guardar detalle de factura: " + ex.getMessage(), ex);
        } finally {
            em.close(); // Cerrando la conexion
        }
    }

    public void actualizarDetalleVenta(DetalleVenta detalleVentaActualizado) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            // Verificar existencia y estado antes de actualizar
            DetalleVenta verificarExistencia = em.find(DetalleVenta.class, detalleVentaActualizado.getIdDetalleVenta());
            if (verificarExistencia == null || !verificarExistencia.isEstado() || !verificarExistencia.getVenta().isEstado()) {
                throw new EntityNotFoundException("Error al actualizar: Detalle no encontrado");
            }

            em.merge(detalleVentaActualizado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al actualizar Detalle: " + ex.getMessage(), ex);
        } finally {
            em.close(); // Cerrando la conexion
        }
    }

    public DetalleVenta buscarDetalleVentaPorId(Long id) {
        EntityManager em = DatabaseUtil.getEntityManager();
        try {
            DetalleVenta detalleVenta = em.find(DetalleVenta.class, id);
            if (detalleVenta == null || !detalleVenta.isEstado()) {
                throw new EntityNotFoundException("Detalle con ID " + id + " no encontrado");
            }
            return detalleVenta;
        } finally {
            em.close(); // Cerrando la conexion
        }
    }

    public List<DetalleVenta> listarPorVenta(Long idVenta) {
        EntityManager em = DatabaseUtil.getEntityManager();
        try {
            return em.createQuery("SELECT dv FROM DetalleVenta dv JOIN FETCH dv.VarianteProductoVenta vpv JOIN FETCH vpv.producto WHERE dv.venta.idVenta = :idVenta AND dv.venta.estado = true AND dv.estado = true", DetalleVenta.class)
                    .setParameter("idVenta", idVenta)
                    .getResultList();
        } finally {
            em.close(); // Cerrando conexion
        }
    }

    public void agregarDetalleAVenta(Long idVenta, DetalleVenta detalleVenta) {
        EntityManager em = DatabaseUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Venta venta = em.find(Venta.class, idVenta);

            if (venta == null || !venta.isEstado()) {
                throw new EntityNotFoundException("Factura no encontrada o inactiva");
            }

            detalleVenta.setVenta(venta);
            em.persist(detalleVenta);
            em.getTransaction().commit();
            System.out.println("Detalle agregado a factura: " + idVenta);
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al agregar detalle: " + ex.getMessage(), ex);
        } finally {
            em.close(); // Cerrando conexion
        }
    }

    public void borrarDetalleVenta(Long idDetalleVenta) {
        EntityManager em = DatabaseUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            DetalleVenta detalleVenta = em.find(DetalleVenta.class, idDetalleVenta);

            if (detalleVenta == null || !detalleVenta.isEstado() || !detalleVenta.getVenta().isEstado()) {
                throw new EntityNotFoundException("Detalle no encontrado o ya eliminado");
            }

            detalleVenta.setEstado(false);

            em.getTransaction().commit();

            System.out.println("Se ha eliminado correctamente el registro");

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("No se ha encontrado el detalle" + nre.getMessage(), nre);

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al eliminar detalle: " + ex.getMessage(), ex);
        } finally {
            em.close(); // Cerrando la conexion
        }

    }

}
