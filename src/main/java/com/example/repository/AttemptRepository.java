package com.example.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.entity.Attempt;
import com.example.entity.Question;
import com.example.entity.Quiz;
import com.example.entity.User;

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

    public List<Attempt> GetAttemptsByQuiz(User user, Quiz quiz) {
        Session session = sessionFactory.openSession();
        Query<Attempt> q = null;
        if (user != null) {
            q = session.createQuery("select a from Attempt a where user=:user and quiz=:quiz", Attempt.class);
            q.setParameter("user", user);
        } else {
            q = session.createQuery("select a from Attempt a where quiz=:quiz", Attempt.class);
        }
        q.setParameter("quiz", quiz);
        List<Attempt> result = q.getResultList();
        session.close();
        return result;
    }

    public void DeleteAttempt(Attempt attempt) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.remove(attempt);
        transaction.commit();
        session.close();
    }
}
