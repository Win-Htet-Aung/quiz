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

    public static void UpdateQuestionShowMenu() {
        System.out.println("1.Edit question");
        System.out.println("2. Add to Quizzes");
        System.out.println("3. Remove from Quizzes");

    }



    public static Question GetQuestionById(Context ctx, Long qid) {
        return ctx.getQuestionRepo().GetQuestionById(qid);
    }

    public static void ShowQuestions(Context ctx) {
        List<Question> questions = ctx.getQuestionRepo().GetAllQuestions();
        for (Question q: questions) {
            System.out.println(q);
        }
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


    public static void EditQuestion(Context ctx, Question question){
        System.out.println("Enter question : ");
        String q = ctx.getSc().nextLine();
        question.setQuestion(q);
        ctx.getQuestionRepo().UpdateQuestion(question);
        UpdateChoice(question, ctx);
    }
    
    public static void addToQuizzes(Context ctx, Question question) {
        System.out.println();
        QuizService.ShowQuizzes(ctx, question, false);
        System.out.print("\nSelect question id(s) ending with '-1' : ");
        // let the user to select
        Long quizzesid;
        do {
            quizzesid = ctx.getSc().nextLong();
            if (quizzesid != -1) {
                // add the selected questions to the quiz
                Quiz quiz = QuizService.GetQuizById(ctx, quizzesid);
                quiz.getQuestions().add(question);
                question.getQuizzes().add(quiz);
            }
        } while (quizzesid != -1);
        ctx.getQuestionRepo().UpdateQuestion(question);
    }


    public static void RemoveFromQuizzes(Context ctx, Question question){
        QuizService.ShowQuizzes(ctx, question, true);
        System.out.print("\nSelect quiz id(s) ending with '-1' : ");

        Long qid;

        do {
            qid = ctx.getSc().nextLong();
            if (qid != -1) {
                // remove from quiz
                Quiz quiz = QuizService.GetQuizById(ctx, qid);
              
                quiz.getQuestions().removeIf(q -> q.getId() == question.getId());
                question.getQuizzes().removeIf(q -> q.getId() == quiz.getId());
            }
        } while (qid != -1);
      
            ctx.getQuestionRepo().UpdateQuestion(question);
        
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
  
    public static void UpdateChoice(Question question, Context ctx){
        System.out.println("Enter answer : ");
        String ans = ctx.getSc().nextLine();

        System.out.println("Enter choice 1 : ");
        String c1 = ctx.getSc().nextLine();

        System.out.println("Enter choice 2 : ");
        String c2 = ctx.getSc().nextLine();

        System.out.println("Enter choice 3 : ");
        String c3 = ctx.getSc().nextLine();

        question.setAnswer(ans);
        question.setChoice1(c1);
        question.setChoice2(c2);
        question.setChoice3(c3);

        ctx.getQuestionRepo().CreateQuestion(question);
    }

    public static void UpdateQuestion(Context ctx) {
        ShowQuestions(ctx);
        System.out.print("\nSelect quiz id : ");
        Long qid = ctx.getSc().nextLong();
        Question question = GetQuestionById(ctx, qid);
        // System.out.println("Editing quiz : " + quiz.getTitle());
        //show
        UpdateQuestionShowMenu();
        System.out.print("\nSelect editing options : ");
        int option = ctx.getSc().nextInt();
        switch (option) {
            case 1:
                EditQuestion(ctx, question);
                //UpdateChoice
                break;
            case 2:
                addToQuizzes(ctx, question);
                break;
            case 3:
                RemoveFromQuizzes(ctx, question);
                break;
            default:
                break;
        }
    }

   
}
