package com.example.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.example.entity.Attempt;

public class AttemptRepository extends Repository {
    public void CreateAttempt(Attempt attempt) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.persist(attempt);
        transaction.commit();
        session.close();
    }

    public void UpdateAttempt(Attempt attempt) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.merge(attempt);
        transaction.commit();
        session.close();
    }
}
