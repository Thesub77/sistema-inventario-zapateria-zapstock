/*
 * @author Douglas Quiroz (@thesub77)
 * @project_name Zapstock
 */
package com.thesub77.zapstock.repository;

import com.thesub77.zapstock.model.OrdenCompra;
import com.thesub77.zapstock.util.DatabaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import java.util.List;

/**
 *
 */
public class OrdenCompraRepository {

    public void guardarOrdenCompra(OrdenCompra ordenCompra) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(ordenCompra);
            em.getTransaction().commit();

            System.out.println("Se ha guardado la orden");

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al guardar Orden: " + ex.getMessage(), ex);
        } finally {

        }
    }

    public void actualizarOrdenCompra(OrdenCompra ordenActualizada) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            OrdenCompra verificarExistencia = em.find(OrdenCompra.class, ordenActualizada.getIdOrdenCompra());

            if (verificarExistencia == null || !verificarExistencia.isEstado()) {
                throw new EntityNotFoundException("Error al actualizar: Orden no encontrada");
            }

            em.merge(ordenActualizada);
            em.getTransaction().commit();

            System.out.println("Se ha actualizado la informacion de la orden");

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al actualizar orden: " + ex.getMessage(), ex);

        } finally {
            em.close();
        }
    }

    public OrdenCompra buscarOrdenPorId(Long idOrdenCompra) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            OrdenCompra orden = em.find(OrdenCompra.class, idOrdenCompra);

            if (orden == null || !orden.isEstado()) {
                throw new EntityNotFoundException("Orden con ID " + idOrdenCompra + " no encontrada");
            }

            return orden;
        } finally {
            em.close();
        }

    }

    public OrdenCompra buscarOrdenPorCodigo(String codigoOrdenCompra) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            return em.createQuery("SELECT oc FROM OrdenCompra oc WHERE oc.codigoOrden = :codigoOrden AND oc.estado = true", OrdenCompra.class)
                    .setParameter("codigoOrden", codigoOrdenCompra)
                    .getSingleResult();

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("Orden con codigo " + codigoOrdenCompra + " no encontrada " + nre.getMessage() + " " + nre);

        } finally {
            em.close();
        }
    }

    public List<OrdenCompra> listarOrdenesCompra() {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            return em.createQuery("SELECT oc FROM OrdenCompra oc JOIN FETCH oc.proveedor WHERE oc.estado = true", OrdenCompra.class)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    public void borrarOrdenCompra(String codigoOrdenCompra) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            OrdenCompra busquedaOrden = em.createQuery("SELECT oc FROM OrdenCompra oc WHERE oc.codigoOrden = :codigoOrdenCompra AND oc.estado = true", OrdenCompra.class)
                    .setParameter("codigoOrdenCompra", codigoOrdenCompra)
                    .getSingleResult();

            busquedaOrden.setEstado(false);

            em.getTransaction().commit();

            System.out.println("Se ha eliminado la orden");

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("No se ha encontrado la orden" + nre.getMessage(), nre);

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al eliminar orden: " + ex.getMessage(), ex);

        } finally {
            em.close();
        }

    }

}
