package com.example;

import java.util.HashMap;

import com.example.services.QuestionService;
import com.example.services.QuizService;
import com.example.services.UserService;
import com.example.utils.Context;
import com.example.utils.OptionsEnum;

public class Main {
    private static Context ctx = new Context();
    private static HashMap<Integer, OptionsEnum> optionMap;

    public static void showMenu() {
        optionMap = new HashMap<>();
        int i = 1;
        System.out.println("    Quiz System");
        System.out.println("====================");
        System.out.println(String.format("%d. Show quizzes", i));
        optionMap.put(i, OptionsEnum.SHOWQUIZZES);
        i++;
        if (ctx.getUser() != null && ctx.getUser().getRole().equals("admin")) {
            System.out.println(String.format("%d. Create quiz", i));
            optionMap.put(i, OptionsEnum.CREATEQUIZ);
            i++;
            System.out.println(String.format("%d. Create question", i));
            optionMap.put(i, OptionsEnum.CREATEQUESTION);
            i++;
        }
        if (ctx.getUser() == null) {
            System.out.println(String.format("%d. Log in", i));
            optionMap.put(i, OptionsEnum.LOGIN);
            i++;
            System.out.println(String.format("%d. Sign up", i));
            optionMap.put(i, OptionsEnum.SIGNUP);
            i++;
        } else {
            System.out.println(String.format("%d. Log out", i));
            optionMap.put(i, OptionsEnum.LOGOUT);
            i++;
        }
        System.out.println(String.format("%d. Exit", i));
        optionMap.put(i, OptionsEnum.EXIT);
        i++;
        System.out.print("\nChoose an option : ");
    }
    public static void main(String[] args) {
        boolean exit = false;
        do {
            showMenu();
            int option = ctx.getSc().nextInt();
            OptionsEnum opt = optionMap.get(option);
            switch (opt) {
                case SHOWQUIZZES:
                    QuizService.ShowQuizzes(ctx);
                    break;
                case CREATEQUIZ:
                    QuizService.CreateQuiz(ctx);
                    break;
                case CREATEQUESTION:
                    QuestionService.CreateQuestion(ctx);
                    break;
                case LOGIN:
                    UserService.Login(ctx);
                    break;
                case SIGNUP:
                    UserService.CreateUser(ctx);
                    break;
                case LOGOUT:
                    UserService.Logout(ctx);
                    break;
                case EXIT:
                    System.out.println("\nGood Bye!");
                    exit = true;
                    break;
                default:
                    break;
            }
        } while(!exit);
        ctx.getSc().close();
    }
}