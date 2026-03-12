/*
 * @author Douglas Quiroz (@thesub77)
 * @project_name Zapstock
 */
package com.thesub77.zapstock.repository;

import com.thesub77.zapstock.model.Reporte;
import com.thesub77.zapstock.util.DatabaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import java.util.List;

/**
 *
 */
public class ReporteRepository {

    public void guardarReporte(Reporte reporte) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(reporte);
            em.getTransaction().commit();
            System.out.println("Se ha creado el reporte " + reporte.getCodigoReporte());

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al crear reporte: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

    public void actualizarReporte(Reporte reporteActualizado) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            // Verificar existencia y estado antes de actualizar
            Reporte verificarExistencia = em.find(Reporte.class, reporteActualizado.getIdReporte());
            if (verificarExistencia == null || !verificarExistencia.isEstado()) {
                throw new EntityNotFoundException("Error al actualizar: Reporte no encontrado");
            }

            em.merge(reporteActualizado);
            em.getTransaction().commit();
            System.out.println("Se ha actualizado el reporte " + reporteActualizado.getCodigoReporte());

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al actualizar reporte: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

    public Reporte buscarReportePorId(Long idReporte) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            Reporte reporte = em.find(Reporte.class, idReporte);

            if (reporte == null || !reporte.isEstado()) {
                throw new EntityNotFoundException("Reporte con ID " + idReporte + " no encontrado");
            }

            return reporte;
        } finally {
            em.close();
        }

    }

    public Reporte buscarReportePorCodigo(String codigoReporte) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            return em.createQuery("SELECT r FROM Reporte r WHERE r.codigoReporte = :codigoReporte AND r.estado = true", Reporte.class)
                    .setParameter("codigoReporte", codigoReporte)
                    .getSingleResult();

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("Reporte " + codigoReporte + " no encontrado " + nre.getMessage() + " " + nre);
        } finally {
            em.close();
        }

    }

    public List<Reporte> listarReportes() {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            return em.createQuery("SELECT r FROM Reporte r WHERE r.estado = true", Reporte.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void borrarReporte(String codigoReporte) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Reporte reporte = em.createQuery("SELECT r FROM Reporte r WHERE r.codigoReporte = :codigoReporte AND r.estado = true", Reporte.class)
                    .setParameter("codigoReporte", codigoReporte)
                    .getSingleResult();

            reporte.setEstado(false);

            em.getTransaction().commit();

            System.out.println("Se ha eliminado el reporte: " + codigoReporte);

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("No se ha encontrado el reporte " + nre.getMessage(), nre);
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

        } finally {
            em.close();
        }

    }

}
