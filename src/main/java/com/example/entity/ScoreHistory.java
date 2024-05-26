package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "scorehistory")
public class ScoreHistory {
    @EmbeddedId
    private AttemptQuestion attempt_question;

    @Column(name = "score")
    private Integer score = 0;
    
    public ScoreHistory() {}

    public AttemptQuestion getAttempt_question() {
        return attempt_question;
    }

    public void setAttempt_question(AttemptQuestion attempt_question) {
        this.attempt_question = attempt_question;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
