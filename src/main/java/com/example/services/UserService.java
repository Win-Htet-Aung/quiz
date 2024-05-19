package com.example.services;

import org.hibernate.exception.ConstraintViolationException;

import com.example.entity.User;
import com.example.utils.Context;

public class UserService {
    public static void Login(Context ctx) {
        ctx.getSc().nextLine();
        System.out.print("\nUsername  : ");
        String username = ctx.getSc().nextLine();
        System.out.print("Password  : ");
        String password = ctx.getSc().nextLine();
        User user = ctx.getUserRepo().GetUserByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                ctx.setUser(user);
                System.out.println(
                    String.format("Logged in as %s successfully! Welcome %s!", user.getRole(), user.getName())
                );
            } else {
                System.out.println("Login failed! Incorrect username and password!");
            }
        } else {
            System.out.println("Login failed! Incorrect username and password!");
        }
    }

    public static void CreateUser(Context ctx) {
        System.out.print("\nUsername  : ");
        String username = ctx.getSc().nextLine();
        System.out.print("Password  : ");
        String password = ctx.getSc().nextLine();
        User user = new User(username, password);
        try {
            ctx.getUserRepo().CreateUser(user);
        } catch (ConstraintViolationException e) {
            System.out.println("Username is already taken!");
        }
    }

    public static void Logout(Context ctx) {
        System.out.println("Logging you out!");
        ctx.setUser(null);
    }
}
