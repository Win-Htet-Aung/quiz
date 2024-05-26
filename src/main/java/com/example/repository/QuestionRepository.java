package com.example.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.entity.Question;
import com.example.entity.Quiz;

public class QuestionRepository extends Repository {
    public void CreateQuestion(Question question) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.persist(question);
        transaction.commit();
        session.close();
    }

    public List<Question> GetAllQuestions() {
        Session session = sessionFactory.openSession();
        Query<Question> q = session.createQuery("select q from Question q", Question.class);
        List<Question> result = q.getResultList();
        session.close();
        return result;
    }

    public Question GetQuestionById(Long qid) {
        Question question = null;
        Session session = sessionFactory.openSession();
        Query<Question> q = session.createQuery("select q from Question q where q.id = :qid", Question.class);
        q.setParameter("qid", qid);
        question = q.getSingleResult();
        session.close();
        return question;
    }

    public void UpdateQuestion(Question question) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.merge(question);
        transaction.commit();
        session.close();
    }

    public void DeleteQuestion(Question question) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.remove(question);
        transaction.commit();
        session.close();
    }

    public List<Question> GetQuestionsByIds(List<Long> qids, boolean included) {
        Session session = sessionFactory.openSession();
        Query<Question> q;
        if (included) {
            q = session.createQuery("select q from Question q where id in (:ids)", Question.class);
        } else {
            q = session.createQuery("select q from Question q where id not in (:ids)", Question.class);
        }
        q.setParameter("ids", qids);
        List<Question> result = q.getResultList();
        session.close();
        return result;
    }

  
}
