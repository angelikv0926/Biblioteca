package com.ceiba.biblioteca.infraestructura.configuracion.conexion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConexionJPA {

    private static final String BIBLIOTECA = "biblioteca";
    private static EntityManagerFactory entityManagerFactory;

    public ConexionJPA() {
        entityManagerFactory = Persistence.createEntityManagerFactory(BIBLIOTECA);
    }

    public EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
