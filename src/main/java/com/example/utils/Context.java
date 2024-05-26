package com.example.utils;

import java.util.Scanner;

import com.example.entity.User;
import com.example.repository.AttemptRepository;
import com.example.repository.QuestionRepository;
import com.example.repository.QuizRepository;
import com.example.repository.ScoreHistoryRepository;
import com.example.repository.UserRepository;

public class Context {
    private User user;
    private Scanner sc = new Scanner(System.in);
    private QuizRepository quizRepo = new QuizRepository();
    private UserRepository userRepo = new UserRepository();
    private QuestionRepository questionRepo = new QuestionRepository();
    private AttemptRepository attemptRepo = new AttemptRepository();
    private ScoreHistoryRepository scorehistoryRepo = new ScoreHistoryRepository();

    public Context() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public QuizRepository getQuizRepo() {
        return quizRepo;
    }

    public UserRepository getUserRepo() {
        return userRepo;
    }

    public QuestionRepository getQuestionRepo() {
        return questionRepo;
    }

    public AttemptRepository getAttemptRepo() {
        return attemptRepo;
    }

    public Scanner getSc() {
        return sc;
    }

    public ScoreHistoryRepository getScorehistoryRepo() {
        return scorehistoryRepo;
    }
}
