package com.camino.flashcard.model;

import java.io.Serializable;
import java.util.Objects;

public class Question implements Serializable {

    private String question;
    private String answer;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == this.getClass() && this.question.equals(((Question) obj).question);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(question);
    }

    @Override
    public String toString() {
        return this.question;
    }
}
