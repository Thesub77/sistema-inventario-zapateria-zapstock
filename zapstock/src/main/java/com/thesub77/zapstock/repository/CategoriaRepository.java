/*
 * @author Douglas Quiroz (@thesub77)
 * @proyect_name Zapstock
 */
package com.thesub77.zapstock.repository;

import com.thesub77.zapstock.model.Categoria;
import com.thesub77.zapstock.util.DatabaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import java.util.List;

/**
 *
 */
public class CategoriaRepository {

    public void guardarCategoria(Categoria categoria) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(categoria);
            em.getTransaction().commit();
            System.out.println("Se ha guardado el registro con exito");
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al guardar categoría: " + ex.getMessage(), ex);
        } finally {
            em.close(); // Cerrando la conexion
        }
    }

    public void actualizarCategoria(Categoria categoriaActualizada) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            // Verificar existencia y estado antes de actualizar
            Categoria verificarExistencia = em.find(Categoria.class, categoriaActualizada.getIdCategoria());
            if (verificarExistencia == null || !verificarExistencia.isEstado()) {
                throw new EntityNotFoundException("Error al actualizar: Categoría no encontrada");
            }

            em.merge(categoriaActualizada);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al actualizar categoría: " + ex.getMessage(), ex);
        } finally {
            em.close(); // Cerrando la conexion
        }
    }

    public Categoria buscarCategoriaPorId(long id) {
        EntityManager em = DatabaseUtil.getEntityManager();
        try {
            Categoria categoria = em.find(Categoria.class, id);
            if (categoria == null || !categoria.isEstado()) {
                throw new EntityNotFoundException("Categoría con ID " + id + " no encontrada");
            }
            return categoria;
        } finally {
            em.close(); // Cerrando la conexion
        }
    }

    public Categoria buscarCategoriaPorNombre(String nombre) {
        EntityManager em = DatabaseUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Categoria c WHERE c.nombreCategoria = :nombre AND c.estado = true", Categoria.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (NoResultException nre) {
            throw new EntityNotFoundException("Categoria " + nombre + " no encontrada " + nre.getMessage() + " " + nre);
        } finally {
            em.close(); // Cerrando la conexion
        }
    }

    public List<Categoria> listarCategorias() {
        EntityManager em = DatabaseUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Categoria c WHERE c.estado = true", Categoria.class).getResultList();
        } finally {
            em.close(); // Cerrando la conexion
        }
    }

    public void borrarCategoria(String nombre) {
        EntityManager em = DatabaseUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Categoria busqueda = em.createQuery("SELECT c FROM Categoria c WHERE c.nombreCategoria = :nombre AND c.estado = true", Categoria.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();

            busqueda.setEstado(false);

            em.getTransaction().commit();

            System.out.println("Se ha eliminado correctamente el registro");

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("No se ha encontrado la categoria" + nre.getMessage(), nre);

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al eliminar categoría: " + ex.getMessage(), ex);
        } finally {
            em.close(); // Cerrando la conexion
        }

    }

}
