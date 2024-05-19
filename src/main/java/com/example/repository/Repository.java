package com.example.repository;

import org.hibernate.SessionFactory;

import com.example.utils.HibernateUtility;

public class Repository {
    protected SessionFactory sessionFactory;
    
    public Repository() {
        this.sessionFactory = HibernateUtility.getSessionFactory();
    }
}
