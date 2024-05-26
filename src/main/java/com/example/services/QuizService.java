package com.example.services;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.example.entity.Attempt;
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
        ShowQuizzes(ctx, null, false);
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
            case 4:
                DeleteQuiz(ctx, quiz);
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

    public static void UpdateQuizShowMenu() {
        System.out.println(String.format("1. Edit quiz title"));
        System.out.println(String.format("2. Add questions"));
        System.out.println(String.format("3. Remove questions"));
        System.out.println(String.format("4. Delete"));
    }

    public static void ShowQuizzes(Context ctx, Question question, boolean included) {
        List<Quiz> quizzes = null;
        if (question == null) {
            quizzes = ctx.getQuizRepo().GetAllQuizzes();
        } else {
            if (included) {
                quizzes = new ArrayList<Quiz>(question.getQuizzes());
            } else {
                List<Long> quizids = new ArrayList<>();
                for (Quiz q: question.getQuizzes()) {
                    quizids.add(q.getId());
                }
                quizzes = ctx.getQuizRepo().GetQuizzesByIds(quizids, included);
            }
        }
        for (Quiz q: quizzes) {
            System.out.println(String.format("%d  %s", q.getId(), q.getTitle()));
        }
    }

    public static void DeleteQuiz(Context ctx, Quiz quiz) {
        List<Question> questions = new ArrayList<>();
        for (Question q : quiz.getQuestions()) {
            questions.add(q);
        }
        quiz.getQuestions().clear();
        for (Question question : questions) {
            question.getQuizzes().removeIf(q -> q.getId() == quiz.getId());
        }
        ctx.getQuizRepo().DeleteQuiz(quiz);
    }

    public static void TakeQuiz(Context ctx) {
        System.out.print("\nDo you want get started to test your knowledge?(y/n) : ");
        ctx.getSc().nextLine();
        String response = ctx.getSc().nextLine();
        if (response.equals("y")) {
            System.out.print("Selete quiz id : ");
            Long quiz_id = ctx.getSc().nextLong();
            if (ctx.getUser() == null) {
                System.out.println("You need to log in first!");
                UserService.Login(ctx);
            }
            if (ctx.getUser() != null) {
                Quiz quiz = GetQuizById(ctx, quiz_id);
                Set<Question> questions = quiz.getQuestions();
                System.out.println(String.format("\nQuiz Title : %s", quiz.getTitle()));
                System.out.println(String.format("There are %d questions in this quiz!\nGood Luck!!!\n", questions.size()));
                int i = 1;
                ctx.getSc().nextLine();
                Attempt attempt = AttemptService.CreateAttempt(ctx, quiz);
                for (Question question : questions) {
                    int point = 0;
                    HashMap<String, String> choice_map = question.display(i);
                    String answer = ctx.getSc().nextLine();
                    if (choice_map.get(answer).equals(question.getAnswer())) {
                        point = 1;
                    }
                    attempt.setScore(attempt.getScore() + point);
                    ScoreHistoryService.CreateScoreHistory(ctx, attempt, question, point);
                    i++;
                }
                AttemptService.UpdateAttempt(ctx, attempt);
                System.out.println(String.format("\nYou got %d points out of %d!\n\nPress Enter to continue!", attempt.getScore(), questions.size()));
                ctx.getSc().nextLine();
            }
        }
    }
}
