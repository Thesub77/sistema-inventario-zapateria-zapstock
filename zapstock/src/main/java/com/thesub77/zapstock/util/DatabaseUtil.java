/*
 * @author Douglas Quiroz (@thesub77)
 * @proyect_name Zapstock
 */
package com.thesub77.zapstock.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 */
public class DatabaseUtil {
    private static EntityManagerFactory emf;
    
    // Inicializar conexiones
    public static void init(){
        if (emf == null){
            try {
                // Carga db.properties del classpath
                Properties props = new Properties();
                InputStream is = DatabaseUtil.class.getClassLoader().getResourceAsStream("db.properties");
                if (is != null) {
                    props.load(is);
                } else {
                    throw new RuntimeException("db.properties no encontrado en resources");
                }

                // Override props en el map para createEntityManagerFactory
                java.util.Map<String, String> map = new java.util.HashMap<>();
                map.put("jakarta.persistence.jdbc.url", props.getProperty("db.url"));
                map.put("jakarta.persistence.jdbc.user", props.getProperty("db.user"));
                map.put("jakarta.persistence.jdbc.password", props.getProperty("db.password"));

                // Crea la factory con overrides
                emf = Persistence.createEntityManagerFactory("zapstockPU", map);
            } catch (Exception e) {
                throw new RuntimeException("Error cargando db.properties: " + e.getMessage(), e);
            }
        }
    }
    
    // Metodo que devuelve una conexion
    public static EntityManager getEntityManager(){
        if (emf == null){
            init();
        }
        
        return emf.createEntityManager();
    }
    
    // Metodo para cerrar conexiones
    public static void close(){
        if (emf != null && emf.isOpen()){
            emf.close();
        }
    }
}
