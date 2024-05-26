package com.example.services;

import com.example.entity.Attempt;
import com.example.entity.Quiz;
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
}
