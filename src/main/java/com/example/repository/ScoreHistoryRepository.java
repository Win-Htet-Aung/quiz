package com.example.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.example.entity.ScoreHistory;

public class ScoreHistoryRepository extends Repository {
    public void CreateScoreHistory(ScoreHistory scorehistory) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.persist(scorehistory);
        transaction.commit();
        session.close();
    }
}
