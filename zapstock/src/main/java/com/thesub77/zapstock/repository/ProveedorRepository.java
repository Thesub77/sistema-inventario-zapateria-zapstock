/*
 * @author Douglas Quiroz (@thesub77)
 * @proyect_name Zapstock
 */
package com.thesub77.zapstock.repository;

import com.thesub77.zapstock.model.Proveedor;
import com.thesub77.zapstock.util.DatabaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import java.util.List;
import org.hibernate.resource.beans.container.internal.NotYetReadyException;

/**
 *
 */
public class ProveedorRepository {

    public void guardarProveedor(Proveedor proveedor) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(proveedor);
            em.getTransaction().commit();
            System.out.println("Se ha guardado el proveedor: " + proveedor.getNombreEmpresa());

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al guardar proveedor: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

    public void actualizarProveedor(Proveedor proveedorActualizado) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(proveedorActualizado);
            em.getTransaction().commit();
            System.out.println("Se ha actualizado el proveedor: " + proveedorActualizado.getNombreEmpresa());

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al actualizar proveedor: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

    public Proveedor buscarProveedorPorId(long id) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            Proveedor prov = em.find(Proveedor.class, id);

            if (prov == null) {
                throw new EntityNotFoundException("Proveedor con ID " + id + " no encontrado");
            }

            return prov;
        } finally {
            em.close();
        }
    }

    public Proveedor buscarProveedorPorNombre(String nombreProveedor) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {

            return em.createQuery("SELECT p FROM Proveedor p WHERE p.nombreEmpresa = :nombre", Proveedor.class)
                    .setParameter("nombre", nombreProveedor)
                    .getSingleResult();

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("Proveedor " + nombreProveedor + " no encontrado " + nre.getMessage() + " " + nre);
        } finally {
            em.close();
        }

    }

    public List<Proveedor> listarProveedores() {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {
            return em.createQuery("SELECT p FROM Proveedor p WHERE p.estado = true", Proveedor.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void borrarProveedor(String nombreProveedor) {
        EntityManager em = DatabaseUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            Proveedor prov = em.createQuery("SELECT p FROM Proveedor WHERE p.nombreEmpresa = :nombreProveedor", Proveedor.class)
                    .setParameter("nombreProveedor", nombreProveedor)
                    .getSingleResult();

            prov.setEstado(false);

            em.getTransaction().commit();

            System.out.println("Se ha eliminado el proveedor: " + nombreProveedor);

        } catch (NoResultException nre) {
            throw new EntityNotFoundException("No se ha encontrado el proveedor " + nre.getMessage(), nre);

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

        } finally {
            em.close();
        }
    }

}
