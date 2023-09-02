package com.camino.flashcard.controller;

import com.camino.flashcard.HelloApplication;
import com.camino.flashcard.model.Model;
import com.camino.flashcard.model.Question;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class QuizController {

    @FXML
    ToggleButton toggle;
    @FXML
    TextArea question;
    @FXML
    TextArea answer;

    Stage stage;
    HelloApplication app;
    Model model;
    Question currentQuestion;

    @FXML
    protected void onMenuReturnList() {
        stage.close();
        app.initialWindow();
    }

    @FXML
    protected void onMenuClose() {
        stage.close();
        model.checkModify();
    }

    @FXML
    protected void onMenuDelete() {
        model.delQuestion(currentQuestion);
    }

    @FXML
    protected void onMenuModify() {
        app.addQuestion(2, currentQuestion);
    }

    @FXML
    protected void onMenuLarge() {
        answer.setFont(new Font(24));
        question.setFont(new Font(24));
    }

    @FXML
    protected void onMenuSmall() {
        answer.setFont(new Font(13));
        question.setFont(new Font(13));
    }

    @FXML
    protected void onMenuStart() {
        model.initQuiz();
        setQuestion(model.getQuestion());
    }

    @FXML
    protected void onMenuStartWrong() {
        model.initQuizWrong();
        setQuestion(model.getQuestion());
    }

    @FXML
    protected void onViewClick() {
        viewAnswer();
    }

    @FXML
    protected void onWrongClick() {
        model.addWrong(currentQuestion);
        setQuestion(model.getQuestion());
        toggle.setSelected(false);
        viewAnswer();
    }

    @FXML
    protected void onCorrectClick() {
        model.incCorrect(currentQuestion);
        setQuestion(model.getQuestion());
        toggle.setSelected(false);
        viewAnswer();
    }

    public void setView(Question newQuestion) {
        question.setText(newQuestion.getQuestion());
        answer.setText(newQuestion.getAnswer());
        toggle.setSelected(true);
    }

    public void init(Stage stage, HelloApplication app, Model model) {
        this.stage = stage;
        this.app = app;
        this.model = model;

        model.quizController = this;
        stage.setOnCloseRequest(e -> {
            model.checkModify();
        });

        if(model.getFile() != null) {
            stage.setTitle("Quiz - " + model.getFile().getName());
        }
        onMenuStart();
    }

    private void viewAnswer() {
        if(toggle.isSelected())
            answer.setText(currentQuestion.getAnswer());
        else
            answer.setText("");
    }

    private void setQuestion(Question q) {
        currentQuestion = q;
        question.setText(currentQuestion.getQuestion());
    }


}
