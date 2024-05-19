package com.example.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.example.entity.Question;

public class QuestionRepository extends Repository {
    public void CreateQuestion(Question question) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.persist(question);
        transaction.commit();
        session.close();
    }
}
