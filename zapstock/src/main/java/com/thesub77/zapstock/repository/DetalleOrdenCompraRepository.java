/*
 * @author Douglas Quiroz (@thesub77)
 * @project_name Zapstock
 */
package com.thesub77.zapstock.repository;

import com.thesub77.zapstock.model.DetalleOrdenCompra;
import com.thesub77.zapstock.model.OrdenCompra;
import com.thesub77.zapstock.util.DatabaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import java.util.List;

/**
 *
 */
public class DetalleOrdenCompraRepository {

    public void guardarDetalleOrden(DetalleOrdenCompra detalleOrden) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(detalleOrden);
            em.getTransaction().commit();
            System.out.println("Se ha guardado el registro con exito");
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al guardar Orden: " + ex.getMessage(), ex);
        } finally {
            em.close(); // Cerrando la conexion
        }
    }

    public void actualizarDetalleOrden(DetalleOrdenCompra detalleActualizado) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            // Verificar existencia y estado antes de actualizar
            DetalleOrdenCompra verificarExistencia = em.find(DetalleOrdenCompra.class, detalleActualizado.getIdDetalleOrden());
            if (verificarExistencia == null || !verificarExistencia.isEstado() || !verificarExistencia.getOrdenCompra().isEstado()) {
                throw new EntityNotFoundException("Error al actualizar: Detalle no encontrado");
            }

            em.merge(detalleActualizado);
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

    public DetalleOrdenCompra buscarDetalleOrdenPorId(Long id) {
        EntityManager em = DatabaseUtil.getEntityManager();
        try {
            DetalleOrdenCompra detalleOrden = em.find(DetalleOrdenCompra.class, id);
            if (detalleOrden == null || !detalleOrden.isEstado()) {
                throw new EntityNotFoundException("Detalle con ID " + id + " no encontrado");
            }
            return detalleOrden;
        } finally {
            em.close(); // Cerrando la conexion
        }
    }

    public List<DetalleOrdenCompra> listarPorOrden(Long idOrdenCompra) {
        EntityManager em = DatabaseUtil.getEntityManager();
        try {
            return em.createQuery("SELECT doc FROM DetalleOrdenCompra doc JOIN FETCH doc.varianteProducto vp JOIN FETCH vp.producto WHERE doc.ordenCompra.idOrdenCompra = :idOrdenCompra AND doc.ordenCompra.estado = true AND doc.estado = true", DetalleOrdenCompra.class)
                    .setParameter("idOrdenCompra", idOrdenCompra)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void agregarDetalleAOrden(Long idOrdenCompra, DetalleOrdenCompra detalle) {
        EntityManager em = DatabaseUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            OrdenCompra orden = em.find(OrdenCompra.class, idOrdenCompra);

            if (orden == null || !orden.isEstado()) {
                throw new EntityNotFoundException("Orden no encontrada o inactiva");
            }

            detalle.setOrdenCompra(orden);
            em.persist(detalle);
            em.getTransaction().commit();
            System.out.println("Detalle agregado a orden " + idOrdenCompra);
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al agregar detalle: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

    public void borrarDetalleOrden(Long idDetalleOrden) {
        EntityManager em = DatabaseUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            DetalleOrdenCompra detalleOrden = em.find(DetalleOrdenCompra.class, idDetalleOrden);

            if (detalleOrden == null || !detalleOrden.isEstado() || !detalleOrden.getOrdenCompra().isEstado()) {
                throw new EntityNotFoundException("Detalle no encontrado o ya eliminado");
            }

            detalleOrden.setEstado(false);

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
