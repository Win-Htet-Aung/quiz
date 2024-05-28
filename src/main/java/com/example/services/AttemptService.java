package com.example.services;

import java.util.List;

import com.example.entity.Attempt;
import com.example.entity.Quiz;
import com.example.entity.User;
import com.example.utils.Context;

public class AttemptService {
    public static Attempt CreateAttempt(Context ctx, Quiz quiz) {
        Attempt attempt = new Attempt();
        attempt.setQuiz(quiz);
        attempt.setScore(0);
        attempt.setUser(ctx.getUser());
        ctx.getAttemptRepo().CreateAttempt(attempt);
        return attempt;
    }

    public static void UpdateAttempt(Context ctx, Attempt attempt) {
        ctx.getAttemptRepo().UpdateAttempt(attempt);
    }

    public static int GetHighestScore(Context ctx, Quiz quiz) {
        int highest_score = -1;
        if (ctx.getUser() != null) {
            highest_score = 0;
            List<Attempt> attempts = ctx.getAttemptRepo().GetAttemptsByQuiz(ctx.getUser(), quiz);
            for (Attempt attempt : attempts) {
                if (attempt.getScore() > highest_score) {
                    highest_score = attempt.getScore();
                }
            }
        }
        return highest_score;
    }
}
