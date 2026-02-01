/*
 * @author Douglas Quiroz (@thesub77)
 * @proyect_name Zapstock
 */
package com.thesub77.zapstock.repository;

import com.thesub77.zapstock.model.Privilegio;
import com.thesub77.zapstock.util.DatabaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import java.util.List;

/**
 *
 */
public class PrivilegioRepository {

    public void guardarPrivilegio(Privilegio privilegio) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(privilegio);
            em.getTransaction().commit();
            System.out.println("Se ha guardado el privilegio " + privilegio.getNombrePrivilegio());

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al guardar privilegio: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

    public void actualizarPrivilegio(Privilegio privilegioActualizado) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            
            // Verificar existencia y estado antes de actualizar
            Privilegio verificarExistencia = em.find(Privilegio.class, privilegioActualizado.getIdPrivilegio());
            if (verificarExistencia == null || !verificarExistencia.isEstado()) {
                throw new EntityNotFoundException("Error al actualizar: Privilegio no encontrado");
            }
            
            em.merge(privilegioActualizado);
            em.getTransaction().commit();
            System.out.println("Se ha actualizado el privilegio " + privilegioActualizado.getNombrePrivilegio());

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al actualizar privilegio: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

    // !!! Modificar para que no mande a traer privilegios eliminados
    public Privilegio buscarPrivilegioPorId(long id) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            Privilegio privilegio = em.find(Privilegio.class, id);

            if (privilegio == null || !privilegio.isEstado()) {
                throw new EntityNotFoundException("Privilegio con ID " + id + " no encontrado");
            }

            return privilegio;
        } finally {
            em.close();
        }

    }

    public Privilegio buscarPrivilegioPorNombre(String nombrePrivilegio) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            return em.createQuery("SELECT pr FROM Privilegio pr WHERE pr.nombrePrivilegio = :nombrePriv AND pr.estado = true", Privilegio.class)
                    .setParameter("nombrePriv", nombrePrivilegio)
                    .getSingleResult();

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("Privilegio " + nombrePrivilegio + " no encontrado " + nre.getMessage() + " " + nre);
        } finally {
            em.close();
        }

    }

    public List<Privilegio> listarPrivilegios() {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            return em.createQuery("SELECT pr FROM Privilegio pr WHERE pr.estado = true", Privilegio.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void borrarPrivilegio(String nombrePrivilegio) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Privilegio privilegio = em.createQuery("SELECT pr FROM Privilegio pr WHERE pr.nombrePrivilegio = :nombrePriv AND pr.estado = true", Privilegio.class)
                    .setParameter("nombrePriv", nombrePrivilegio)
                    .getSingleResult();

            privilegio.setEstado(false);

            em.getTransaction().commit();

            System.out.println("Se ha eliminado el privilegio: " + nombrePrivilegio);

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("No se ha encontrado el privilegio " + nre.getMessage(), nre);
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

        } finally {
            em.close();
        }

    }

}
