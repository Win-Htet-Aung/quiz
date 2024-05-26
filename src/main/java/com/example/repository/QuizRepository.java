package com.example.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.entity.Question;
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

    public void UpdateQuiz(Quiz quiz) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.merge(quiz);
        transaction.commit();
        session.close();
    }

    public Quiz GetQuizById(Long qid) {
        Quiz quiz = null;
        Session session = sessionFactory.openSession();
        Query<Quiz> q = session.createQuery("select q from Quiz q where q.id = :qid", Quiz.class);
        q.setParameter("qid", qid);
        quiz = q.getSingleResult();
        session.close();
        return quiz;
    }

    public List<Quiz> GetQuizzesByIds(List<Long> qids, boolean included) {
        Session session = sessionFactory.openSession();
        Query<Quiz> q;
        if (included) {
            q = session.createQuery("select q from Quiz q where id in (:ids)", Quiz.class);
        } else {
            q = session.createQuery("select q from Quiz q where id not in (:ids)", Quiz.class);
        }
        q.setParameter("ids", qids);
        List<Quiz> result = q.getResultList();
        session.close();
        return result;
    }
}
