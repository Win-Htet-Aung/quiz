package com.example.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            QuestionService.ShowQuestions(ctx, null, false);
            System.out.print("\nSelect question id(s) ending with '-1' : ");
            
            // let the user to select
            Long qid;
            do {
                qid = ctx.getSc().nextLong();
                if (qid != -1) {
                    // add the selected questions to the quiz
                    Question question = QuestionService.GetQuestionById(ctx, qid);
                    quiz.getQuestions().add(question);
                    question.getQuizzes().add(quiz);
                }
            } while (qid != -1);
        }
        ctx.getQuizRepo().CreateQuiz(quiz);
    }

    public static void UpdateQuiz(Context ctx) {
        ShowQuizzes(ctx);
        System.out.print("\nSelect quiz id : ");
        Long qid = ctx.getSc().nextLong();
        Quiz quiz = GetQuizById(ctx, qid);
        System.out.println("Editing quiz : " + quiz.getTitle());
        UpdateQuizShowMenu();
        System.out.print("\nSelect editing options : ");
        int option = ctx.getSc().nextInt();
        switch (option) {
            case 1:
                EditQuizTitle(ctx, quiz);
                break;
            case 2:
                AddQuestions(ctx, quiz);
                break;
            case 3:
                RemoveQuestions(ctx, quiz);
                break;
            default:
                break;
        }
    }

    public static void EditQuizTitle(Context ctx, Quiz quiz) {
        System.out.print("Enter new title : ");
        ctx.getSc().nextLine();
        String title = ctx.getSc().nextLine();
        quiz.setTitle(title);
        ctx.getQuizRepo().UpdateQuiz(quiz);
    }

    public static void AddQuestions(Context ctx, Quiz quiz) {
        System.out.println();
        QuestionService.ShowQuestions(ctx, quiz, false);
        System.out.print("\nSelect question id(s) ending with '-1' : ");
        // let the user to select
        Long qid;
        do {
            qid = ctx.getSc().nextLong();
            if (qid != -1) {
                // add the selected questions to the quiz
                Question question = QuestionService.GetQuestionById(ctx, qid);
                quiz.getQuestions().add(question);
                question.getQuizzes().add(quiz);
            }
        } while (qid != -1);
        ctx.getQuizRepo().UpdateQuiz(quiz);
    }

    public static void RemoveQuestions(Context ctx, Quiz quiz) {
        System.out.println();
        QuestionService.ShowQuestions(ctx, quiz, true);
        System.out.print("\nSelect question id(s) ending with '-1' : ");
        Long qid;
        do {
            qid = ctx.getSc().nextLong();
            if (qid != -1) {
                // remove the selected questions from the quiz
                Question question = QuestionService.GetQuestionById(ctx, qid);
                quiz.getQuestions().removeIf(q -> q.getId() == question.getId());
                question.getQuizzes().removeIf(q -> q.getId() == quiz.getId());
            }
        } while (qid != -1);
        ctx.getQuizRepo().UpdateQuiz(quiz);
    }

    public static Quiz GetQuizById(Context ctx, Long qid) {
        return ctx.getQuizRepo().GetQuizById(qid);
    }

    public static void ShowQuizzes(Context ctx) {
        List<Quiz> quizzes = ctx.getQuizRepo().GetAllQuizzes();
        for (Quiz q: quizzes) {
            System.out.println(q);
        }
    }

    public static void UpdateQuizShowMenu() {
        System.out.println(String.format("1. Edit quiz title"));
        System.out.println(String.format("2. Add questions"));
        System.out.println(String.format("3. Remove questions"));
    }
}
