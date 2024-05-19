package com.example.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.entity.Quiz;

public class QuizRepository extends Repository {

    public List<Quiz> GetAllQuizzes() {
        Session session = sessionFactory.openSession();
        Query<Quiz> q = session.createQuery("select q from Quiz q", Quiz.class);
        List<Quiz> result = q.getResultList();
        session.close();
        return result;
    }

    public void CreateQuiz(Quiz quiz) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.persist(quiz);
        transaction.commit();
        session.close();
    }
}
