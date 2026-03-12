/*
 * @author Douglas Quiroz (@thesub77)
 * @project_name Zapstock
 */
package com.thesub77.zapstock.repository;

import com.thesub77.zapstock.model.Rol;
import com.thesub77.zapstock.model.RolPrivilegio;
import com.thesub77.zapstock.util.DatabaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import java.util.List;

/**
 *
 */
public class RolRepository {

    public void guardarRol(Rol rol) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(rol);
            em.getTransaction().commit();
            System.out.println("Se ha guardado el rol: " + rol.getNombreRol());

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al guardar rol: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

    public void actualizarRol(Rol rolActualizado) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            // Verificar existencia y estado antes de actualizar
            Rol verificarExistencia = em.find(Rol.class, rolActualizado.getIdRol());
            if (verificarExistencia == null || !verificarExistencia.isEstado()) {
                throw new EntityNotFoundException("Error al actualizar: Rol no encontrada");
            }

            em.merge(rolActualizado);
            em.getTransaction().commit();
            System.out.println("Se ha actualizado el rol: " + rolActualizado.getNombreRol());
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al actualizar rol: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

    public Rol buscarRolPorId(Long id) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            Rol rol = em.find(Rol.class, id);

            if (rol == null || !rol.isEstado()) {
                throw new EntityNotFoundException("Rol con ID " + id + " no encontrado");
            }

            return rol;
        } finally {
            em.close();
        }

    }

    public Rol buscarRolPorNombre(String nombreRol) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            return em.createQuery("SELECT r FROM Rol r WHERE r.nombreRol = :nombreRol AND r.estado = true", Rol.class)
                    .setParameter("nombreRol", nombreRol)
                    .getSingleResult();
        } catch (NoResultException nre) {
            throw new EntityNotFoundException("Rol " + nombreRol + " no encontrado " + nre.getMessage() + " " + nre);
        } finally {
            em.close();
        }

    }

    public List<Rol> listarRoles() {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            return em.createQuery("SELECT r FROM Rol r WHERE r.estado = true", Rol.class).getResultList();
        } finally {
            em.close();
        }

    }

    public void borrarRol(String nombreRol) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Rol rol = em.createQuery("SELECT r FROM Rol r WHERE r.nombreRol = :nombreRol AND r.estado = true", Rol.class)
                    .setParameter("nombreRol", nombreRol)
                    .getSingleResult();

            rol.setEstado(false);

            for (RolPrivilegio rp : rol.getPrivilegios()) {
                rp.setEstado(false);
            }

            em.getTransaction().commit();

            System.out.println("Se ha eliminado el rol: " + nombreRol);

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("No se ha encontrado el rol " + nre.getMessage(), nre);
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

        } finally {
            em.close();
        }
    }

}
