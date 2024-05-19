package com.example.services;

import java.util.List;

import com.example.entity.Quiz;
import com.example.utils.Context;

public class QuizService {
    public static void CreateQuiz(Context ctx) {
        ctx.getSc().nextLine();
        System.out.print("\nEnter quiz title : ");
        String title = ctx.getSc().nextLine();
        Quiz quiz = new Quiz(title);
        ctx.getQuizRepo().CreateQuiz(quiz);
        System.out.print("\nDo you want to add questions to this quiz?(y/n) : ");
    }

    public static void ShowQuizzes(Context ctx) {
        List<Quiz> quizzes = ctx.getQuizRepo().GetAllQuizzes();
        for (Quiz q: quizzes) {
            System.out.println(q);
        }
    }
}
