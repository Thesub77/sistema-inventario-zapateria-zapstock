/*
 * @author Douglas Quiroz (@thesub77)
 * @project_name Zapstock
 */
package com.thesub77.zapstock.repository;

import com.thesub77.zapstock.model.VarianteProducto;
import com.thesub77.zapstock.util.DatabaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import java.util.List;

/**
 *
 */
public class VarianteProductoRepository {

    public void guardarVarianteProducto(VarianteProducto variante) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(variante);
            em.getTransaction().commit();

            System.out.println("Se ha guardado la variante: " + variante.getCodigoVariante());

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al guardar variante: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }

    }

    public void actualizarVarianteProducto(VarianteProducto varianteActualizada) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            VarianteProducto verificarExistencia = em.find(VarianteProducto.class, varianteActualizada.getIdVariante());

            if (verificarExistencia == null || !verificarExistencia.isEstado()) {
                throw new EntityNotFoundException("Error al actualizar: Variante no encontrada");
            }

            em.merge(varianteActualizada);
            em.getTransaction().commit();

            System.out.println("Se ha actualizado la variante");

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al actualizar variante: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }

    }

    public VarianteProducto buscarVariantePorId(Long idVarianteProducto) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            VarianteProducto variante = em.find(VarianteProducto.class, idVarianteProducto);

            if (variante == null || !variante.isEstado()) {
                throw new EntityNotFoundException("Variante con ID " + idVarianteProducto + " no encontrada");
            }

            return variante;
        } finally {
            em.close();
        }

    }

    public VarianteProducto buscarVariantePorCodigo(String codigoVariante) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            return em.createQuery("SELECT v FROM VarianteProducto v WHERE v.codigoVariante = :codigoVariante AND v.estado = true", VarianteProducto.class)
                    .setParameter("codigoVariante", codigoVariante)
                    .getSingleResult();

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("Variante con codigo " + codigoVariante + " no encontrada " + nre.getMessage() + " " + nre);
        } finally {
            em.close();
        }

    }

    public List<VarianteProducto> listarVariantes() {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            return em.createQuery("SELECT v FROM VarianteProducto v JOINT FETCH v.producto WHERE v.estado = true", VarianteProducto.class)
                    .getResultList();
        } finally {
            em.close();
        }

    }

    public void borrarVarianteProducto(String codigoVariante) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            VarianteProducto varianteBusqueda = em.createQuery("SELECT v FROM VarianteProducto v WHERE v.codigoVariante = :codigoVariante AND v.estado = true", VarianteProducto.class)
                    .setParameter("codigoVariante", codigoVariante)
                    .getSingleResult();

            varianteBusqueda.setEstado(false);

            em.getTransaction().commit();

            System.out.println("Se ha eliminado la variante con codigo " + codigoVariante);

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("No se ha encontrado la variante" + nre.getMessage(), nre);

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al eliminar variante: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }

    }

}
