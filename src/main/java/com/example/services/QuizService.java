package com.example.services;

import java.util.List;

import com.example.entity.Question;
import com.example.entity.Quiz;
import com.example.utils.Context;

public class QuizService {
    public static void CreateQuiz(Context ctx) {
        ctx.getSc().nextLine();
        System.out.print("\nEnter quiz title : ");
        String title = ctx.getSc().nextLine();
        Quiz quiz = new Quiz(title);
        System.out.print("\nDo you want to add questions to this quiz?(y/n) : ");
        String response = ctx.getSc().nextLine();
        if (response.equals("y")) {
            // show question ids
            QuestionService.ShowQuestions(ctx);
            System.out.print("\nSelect question id(s) : ");
            
            // let the user to select
            Long qid = ctx.getSc().nextLong();
            
            // add the selected questions to the quiz
            Question question = QuestionService.GetQuestionById(ctx, qid);
            quiz.getQuestions().add(question);
            question.getQuizzes().add(quiz);
        }
        ctx.getQuizRepo().CreateQuiz(quiz);
    }

    public static void ShowQuizzes(Context ctx) {
        List<Quiz> quizzes = ctx.getQuizRepo().GetAllQuizzes();
        for (Quiz q: quizzes) {
            System.out.println(q);
        }
    }
}
