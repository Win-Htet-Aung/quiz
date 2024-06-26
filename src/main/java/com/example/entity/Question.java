package com.example.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "question")
    private String question;

    @Column(name = "choice1")
    private String choice1;

    @Column(name = "choice2")
    private String choice2;

    @Column(name = "choice3")
    private String choice3;

    @Column(name = "answer")
    private String answer;

    @ManyToMany(mappedBy = "questions", fetch = FetchType.EAGER)
    private Set<Quiz> quizzes = new HashSet<>();

    public Question() {}

    public Question(String question, String choice1, String choice2, String choice3, String answer) {
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Set<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Set<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public HashMap<String, String> display(int i) {
        System.out.println(String.format("%d. %s", i, question));
        List<String> choices = new ArrayList<>();
        choices.add(answer);
        choices.add(choice1);
        choices.add(choice2);
        choices.add(choice3);
        Collections.shuffle(choices);
        HashMap<String, String> choice_map = new HashMap<>();
        String[] abcd = new String[]{"a", "b", "c", "d"};
        int k = 0;
        for (String choice : choices) {
            System.out.println(String.format(" %s. %s", abcd[k], choice));
            choice_map.put(abcd[k], choice);
            k++;
        }
        System.out.print("Select an answer (a/b/c/d) : ");
        return choice_map;
    }
}
