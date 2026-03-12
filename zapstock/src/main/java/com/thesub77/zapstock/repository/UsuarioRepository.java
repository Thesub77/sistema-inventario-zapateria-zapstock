/*
 * @author Douglas Quiroz (@thesub77)
 * @project_name Zapstock
 */
package com.thesub77.zapstock.repository;

import com.thesub77.zapstock.model.RolPrivilegio;
import com.thesub77.zapstock.model.Usuario;
import com.thesub77.zapstock.util.DatabaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import java.util.List;

/**
 *
 */
public class UsuarioRepository {

    public void guardarUsuario(Usuario usuario) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
            System.out.println("Se ha creado el usuario bajo el correo " + usuario.getCorreoElectronico());

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al crear usuario: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

    public void actualizarUsuario(Usuario usuarioActualizado) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            // Verificar existencia y estado antes de actualizar
            Usuario verificarExistencia = em.find(Usuario.class, usuarioActualizado.getIdUsuario());
            if (verificarExistencia == null || !verificarExistencia.isEstado()) {
                throw new EntityNotFoundException("Error al actualizar: Usuario no encontrado");
            }

            em.merge(usuarioActualizado);
            em.getTransaction().commit();
            System.out.println("Se ha actualizado el usuario " + usuarioActualizado.getCorreoElectronico());

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al actualizar usuario: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

    public Usuario buscarUsuarioPorId(Long idUsuario) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            Usuario usuario = em.find(Usuario.class, idUsuario);

            if (usuario == null || !usuario.isEstado()) {
                throw new EntityNotFoundException("Usuario con ID " + idUsuario + " no encontrado");
            }

            return usuario;
        } finally {
            em.close();
        }

    }

    public Usuario buscarUsuarioPorCodigo(String codigoUsuario) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.codigoUsuario = :codigoUsuario AND u.estado = true", Usuario.class)
                    .setParameter("codigoUsuario", codigoUsuario)
                    .getSingleResult();

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("Usuario " + codigoUsuario + " no encontrado " + nre.getMessage() + " " + nre);
        } finally {
            em.close();
        }

    }

    public List<Usuario> listarUsuarios() {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.estado = true", Usuario.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void borrarUsuario(String codigoUsuario) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.codigoUsuario = :codigoUsuario AND u.estado = true", Usuario.class)
                    .setParameter("codigoUsuario", codigoUsuario)
                    .getSingleResult();

            usuario.setEstado(false);

            for (RolPrivilegio rp : usuario.getRolprivilegio()) {
                rp.setEstado(false);
            }

            em.getTransaction().commit();

            System.out.println("Se ha eliminado el usuario: " + codigoUsuario);

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("No se ha encontrado el usuario" + nre.getMessage(), nre);
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

        } finally {
            em.close();
        }

    }

}
