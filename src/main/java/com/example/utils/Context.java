package com.example.utils;

import java.util.Scanner;

import com.example.entity.User;
import com.example.repository.QuestionRepository;
import com.example.repository.QuizRepository;
import com.example.repository.UserRepository;

public class Context {
    private User user;
    private Scanner sc = new Scanner(System.in);
    private QuizRepository quizRepo = new QuizRepository();
    private UserRepository userRepo = new UserRepository();
    private QuestionRepository questionRepo = new QuestionRepository();

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

    public Scanner getSc() {
        return sc;
    }
}
