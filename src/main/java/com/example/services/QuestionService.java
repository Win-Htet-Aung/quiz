package com.example.services;

import java.util.ArrayList;
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
        String response = ctx.getSc().nextLine();
        if (response.equals("y")) {
            // show quiz ids
            QuizService.ShowQuizzes(ctx);
            System.out.print("\nSelect quiz id(s) ending with '-1' : ");
            
            // let the user to select
            Long qid;
            ArrayList<Quiz> quizzes = new ArrayList<>();
            Quiz quiz;
            do {
                qid = ctx.getSc().nextLong();
                if (qid != -1) {
                    // add the question to the selected quizzes
                    quiz = QuizService.GetQuizById(ctx, qid);
                    quizzes.add(quiz);
                    System.out.println("Adding quiz id : " + quiz.getId());
                    quiz.getQuestions().add(question);
                    question.getQuizzes().add(quiz);
                }
            } while (qid != -1);
            for (Quiz q : quizzes) {
                ctx.getQuizRepo().UpdateQuiz(q);
            }
        }
    }

    public static void UpdateQuestion(Context ctx) {

    }

    public static Question GetQuestionById(Context ctx, Long qid) {
        return ctx.getQuestionRepo().GetQuestionById(qid);
    }

    public static void ShowQuestions(Context ctx, Quiz quiz, boolean included) {
        List<Question> questions = null;
        if (quiz == null) {
            questions = ctx.getQuestionRepo().GetAllQuestions();
        } else {
            if (included) {
                questions = new ArrayList<Question>(quiz.getQuestions());
            } else {
                List<Long> qids = new ArrayList<>();
                for (Question q: quiz.getQuestions()) {
                    qids.add(q.getId());
                }
                questions = ctx.getQuestionRepo().GetQuestionsByIds(qids, included);
            }
        }
        for (Question q: questions) {
            System.out.println(String.format("%d  %s", q.getId(), q.getQuestion()));
        }
    }
}
