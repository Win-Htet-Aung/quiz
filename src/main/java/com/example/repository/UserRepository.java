package com.example.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.entity.User;

import jakarta.persistence.NoResultException;

public class UserRepository extends Repository {
    public User GetUserByUsername(String username) {
        Session session = sessionFactory.openSession();
        Query<User> q = session.createQuery("select u from User u where u.name = :name", User.class);
        q.setParameter("name", username);
        User user = null;
        try {
            user = q.getSingleResult();
        } catch (NoResultException e) {}
        session.close();
        return user;
    }

    public void CreateUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.persist(user);
        transaction.commit();
        session.close();
    }
}
