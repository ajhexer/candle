package com.evaluator.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;


    /**
     * @return Hibernate session factory
     */
    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory != null) {
            return sessionFactory;
        }
        sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }


}
