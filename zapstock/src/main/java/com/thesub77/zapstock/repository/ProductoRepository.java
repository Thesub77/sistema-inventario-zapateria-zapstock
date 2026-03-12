/*
 * @author Douglas Quiroz (@thesub77)
 * @project_name Zapstock
 */
package com.thesub77.zapstock.repository;

import com.thesub77.zapstock.model.Producto;
import com.thesub77.zapstock.util.DatabaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import java.util.List;

/**
 *
 */
public class ProductoRepository {

    public void guardarProducto(Producto producto) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();
            System.out.println("Se ha guardado el registro con exito");

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al guardar producto: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

    public void actualizarProducto(Producto productoActualizado) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            // Verificar existencia y estado antes de actualizar
            Producto verificarExistencia = em.find(Producto.class, productoActualizado.getIdProducto());
            if (verificarExistencia == null || !verificarExistencia.isEstado()) {
                throw new EntityNotFoundException("Error al actualizar: Producto no encontrado");
            }

            em.merge(productoActualizado);
            em.getTransaction().commit();
            System.out.println("Se ha actualizado el producto: " + productoActualizado.getNombreCalzado());
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al actualizar producto: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }

    }

    public Producto buscarProductoPorId(Long idProducto) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            Producto producto = em.find(Producto.class, idProducto);

            if (producto == null || !producto.isEstado()) {
                throw new EntityNotFoundException("Producto con ID " + idProducto + " no encontrada");
            }

            return producto;
        } finally {
            em.close();
        }
    }

    public Producto buscarProductoPorCodigo(String codigoProducto) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            return em.createQuery("SELECT pro FROM Producto pro WHERE pro.codigoProducto = :codigoProducto AND pro.estado = true", Producto.class)
                    .setParameter("codigoProducto", codigoProducto)
                    .getSingleResult();
        } catch (NoResultException nre) {
            throw new EntityNotFoundException("Producto con codigo " + codigoProducto + " no encontrado " + nre.getMessage() + " " + nre);
        } finally {
            em.close();
        }
    }

    public List<Producto> listarProductos() {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            return em.createQuery("SELECT pro FROM Producto pro JOIN FETCH pro.categoria WHERE pro.estado = true", Producto.class)
                    .getResultList();
        } finally {
            em.close();
        }

    }

    public void borrarProducto(String codigoProducto) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            Producto producto = em.createQuery("SELECT pro FROM Producto pro WHERE pro.codigoProducto = :codigoProducto AND pro.estado = true", Producto.class)
                    .setParameter("codigoProducto", codigoProducto)
                    .getSingleResult();

            producto.setEstado(false);

            em.getTransaction().commit();

            System.out.println("Se ha eliminado el producto");

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("No se ha encontrado el producto" + nre.getMessage(), nre);

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al eliminar producto: " + ex.getMessage(), ex);

        } finally {
            em.close();
        }

    }
}
