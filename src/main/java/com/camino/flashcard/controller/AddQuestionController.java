package com.camino.flashcard.controller;

import com.camino.flashcard.HelloApplication;
import com.camino.flashcard.model.Model;
import com.camino.flashcard.model.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AddQuestionController {

    @FXML
    TextArea question;
    @FXML
    TextArea answer;

    Model model;
    Stage stage;
    HelloApplication app;
    int mode;
    Question oldQuestion;

    @FXML
    protected void onSave() {
        Question q = new Question(question.getText(), answer.getText());
        if(!q.getQuestion().equals("") && !q.getAnswer().equals("")) {
            if(mode == 0) {
                model.addQuestion(q);
                question.setText("");
                answer.setText("");
            }
            else {
                model.modifyQuestion(oldQuestion, q, mode - 1);
                stage.close();
            }
        }
    }

    public void init(Model model, Stage stage, HelloApplication app, int mode, Question oldQuestion) {
        this.model = model;
        this.stage = stage;
        this.app = app;
        this.mode = mode;

        if(mode > 0) {
            this.oldQuestion = oldQuestion;
            question.setText(this.oldQuestion.getQuestion());
            answer.setText((this.oldQuestion.getAnswer()));
        }
    }
}
