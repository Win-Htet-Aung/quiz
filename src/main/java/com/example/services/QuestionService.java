package com.example.services;

import java.util.List;

import com.example.entity.Question;
import com.example.entity.Quiz;
import com.example.utils.Context;

public class QuestionService {
    public static void CreateQuestion(Context ctx) {
        ctx.getSc().nextLine();
        System.out.print("Enter question : ");
        String question_text = ctx.getSc().nextLine();
        System.out.print("Answer : ");
        String answer = ctx.getSc().nextLine();
        System.out.print("Choice 1 : ");
        String c1 = ctx.getSc().nextLine();
        System.out.print("Choice 2 : ");
        String c2 = ctx.getSc().nextLine();
        System.out.print("Choice 3 : ");
        String c3 = ctx.getSc().nextLine();
        Question question = new Question(question_text, c1, c2, c3, answer);
        ctx.getQuestionRepo().CreateQuestion(question);
        System.out.print("\nDo you want to add this question to a quiz?(y/n) : ");
    }

    public static Question GetQuestionById(Context ctx, Long qid) {
        return ctx.getQuestionRepo().GetQuestionById(qid);
    }

    public static void ShowQuestions(Context ctx) {
        List<Question> questions = ctx.getQuestionRepo().GetAllQuestions();
        for (Question q: questions) {
            System.out.println(String.format("%d  %s", q.getId(), q.getQuestion()));
        }
    }
}
