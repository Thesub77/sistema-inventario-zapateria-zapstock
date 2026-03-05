/*
 * @author Douglas Quiroz (@thesub77)
 * @project_name Zapstock
 */
package com.thesub77.zapstock.repository;

import com.thesub77.zapstock.model.RolPrivilegio;
import com.thesub77.zapstock.util.DatabaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;

/**
 *
 */
public class RolPrivilegioRepository {

    public void guardarRolPrivilegio(RolPrivilegio rolP) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(rolP);
            em.getTransaction().commit();
            System.out.println("Se ha guardado el rolPriv");

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al guardar rolPriv: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

    public void actualizarRolPrivilegio(RolPrivilegio rolPactualizado) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            // Verificar existencia y estado antes de actualizar
            RolPrivilegio verificarExistencia = em.find(RolPrivilegio.class, rolPactualizado.getIdRolPrivilegio());
            if (verificarExistencia == null || !verificarExistencia.isEstado()) {
                throw new EntityNotFoundException("Error al actualizar: RolPriv no encontrada");
            }

            em.merge(rolPactualizado);
            em.getTransaction().commit();
            System.out.println("Se ha actualizado el rolPriv");
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al actualizar rolPriv: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

    public RolPrivilegio buscarRolPporId(Long id) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            RolPrivilegio rolP = em.find(RolPrivilegio.class, id);

            if (rolP == null || !rolP.isEstado()) {
                throw new EntityNotFoundException("RolP con ID " + id + " no encontrado");
            }

            return rolP;
        } finally {
            em.close();
        }

    }

    public void desasignarPrivilegio(Long idUsuario, Long idPrivilegio) {
        EntityManager em = DatabaseUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            RolPrivilegio desasignacion = em.createQuery(
                    "SELECT rp FROM RolPrivilegio rp WHERE rp.usuario.idUsuario = :idUsuario AND rp.privilegio.idPrivilegio = :idPrivilegio AND rp.estado = true", RolPrivilegio.class)
                    .setParameter("idUsuario", idUsuario)
                    .setParameter("idPrivilegio", idPrivilegio)
                    .getSingleResult();

            desasignacion.setEstado(false);

            em.getTransaction().commit();

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("No se ha encontrado el registro" + nre.getMessage(), nre);
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al desasignar privilegio", ex);
        } finally {
            em.close();
        }
    }

}
