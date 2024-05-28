package com.example.services;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;

import com.example.entity.Attempt;
import com.example.entity.Quiz;
import com.example.entity.User;
import com.example.utils.Context;

public class UserService {
    public static void Login(Context ctx) {
        ctx.getSc().nextLine();
        System.out.print("\nUsername  : ");
        String username = ctx.getSc().nextLine();
        System.out.print("Password  : ");
        String password = ctx.getSc().nextLine();
        User user = ctx.getUserRepo().GetUserByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                ctx.setUser(user);
                System.out.println(
                    String.format("Logged in as %s successfully! Welcome %s!", user.getRole(), user.getName())
                );
            } else {
                System.out.println("Login failed! Incorrect username and password!");
            }
        } else {
            System.out.println("Login failed! Incorrect username and password!");
        }
    }

    public static void CreateUser(Context ctx) {
        System.out.print("\nUsername  : ");
        String username = ctx.getSc().nextLine();
        System.out.print("Password  : ");
        String password = ctx.getSc().nextLine();
        User user = new User(username, password);
        try {
            ctx.getUserRepo().CreateUser(user);
        } catch (ConstraintViolationException e) {
            System.out.println("Username is already taken!");
        }
    }

    public static void Logout(Context ctx) {
        System.out.println("Logging you out!");
        ctx.setUser(null);
    }

    public static void ScoreHistory(Context ctx) {
        QuizService.ShowQuizzes(ctx, null, false);
        System.out.print("\nSelect a quiz id : ");
        Long qid = ctx.getSc().nextLong();
        Quiz quiz = QuizService.GetQuizById(ctx, qid);
        List<Attempt> attempts = ctx.getAttemptRepo().GetAttemptsByQuiz(ctx.getUser(), quiz);
        System.out.println("\nAttempt ID              Score");
        for (Attempt attempt : attempts) {
            System.out.println(String.format("  %-5d%20d  ", attempt.getId(), attempt.getScore()));
        }
        System.out.println("\nPress Enter to continue!");
        ctx.getSc().nextLine();
        ctx.getSc().nextLine();
    }
}
