package com.example.services;

import com.example.entity.Question;
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
    }
}
