package com.backend.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory != null) {
            return sessionFactory;
        }
        sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }
}
