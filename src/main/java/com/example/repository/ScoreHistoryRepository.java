package com.example.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.entity.Attempt;
import com.example.entity.AttemptQuestion;
import com.example.entity.Question;
import com.example.entity.Quiz;
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

    public List<ScoreHistory> GetScoreHistoryByAttempt(Attempt attempt) {
        Session session = sessionFactory.openSession();
        Query<ScoreHistory> q = session.createNativeQuery("select * from scorehistory where attempt_id=:attempt_id", ScoreHistory.class);
        q.setParameter("attempt_id", attempt.getId());
        List<ScoreHistory> result = q.getResultList();
        session.close();
        return result;
    }

    public List<ScoreHistory> GetScoreHistoryByQuestion(Question question) {
        Session session = sessionFactory.openSession();
        Query<ScoreHistory> q = session.createNativeQuery("select * from scorehistory where question_id=:question_id", ScoreHistory.class);
        q.setParameter("question_id", question.getId());
        List<ScoreHistory> result = q.getResultList();
        session.close();
        return result;
    }

    public void DeleteScoreHistory(ScoreHistory score_history) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.remove(score_history);
        transaction.commit();
        session.close();
    }
}
