package com.example.services;

import com.example.entity.ScoreHistory;
import com.example.entity.Attempt;
import com.example.entity.AttemptQuestion;
import com.example.entity.Question;
import com.example.utils.Context;

public class ScoreHistoryService {
    public static void CreateScoreHistory(Context ctx, Attempt attempt, Question question, int score) {
        ScoreHistory scorehistory = new ScoreHistory();
        AttemptQuestion attemptQuestion = new AttemptQuestion();
        attemptQuestion.setAttempt(attempt);
        attemptQuestion.setQuestion(question);
        scorehistory.setAttempt_question(attemptQuestion);
        scorehistory.setScore(score);
        ctx.getScorehistoryRepo().CreateScoreHistory(scorehistory);
    }
}
