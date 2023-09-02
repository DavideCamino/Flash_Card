package com.camino.flashcard.controller;

import com.camino.flashcard.HelloApplication;
import com.camino.flashcard.model.Model;
import com.camino.flashcard.model.Question;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class InitialController {

    @FXML
    ListView<Question> list;
    @FXML
    TextArea question;
    @FXML
    TextArea answer;

    Model model;
    HelloApplication app;
    Stage stage;
    Question currentQuestion;

    @FXML
    protected void onMenuNew() {
        if(model.isModify()) {
            app.promptSave(2);
            stage.close();
        }
        else {
            newFile();

        }
    }
    @FXML
    protected void onMenuSaveAs () {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showSaveDialog(stage);
        if(file != null) {
            model.setFile(file);
            model.saveAll();
            stage.setTitle("Flash Card - " + model.getFile().getName());
        }
    }

    @FXML
    protected void onMenuOpen () {
        if(model.isModify()) {
            app.promptSave(1);
            stage.close();
        }
        else {
            open();
        }
        if (model.getFile() != null)
            stage.setTitle("Flash Card - " + model.getFile().getName());
    }

    @FXML
    protected void onMenuMerge () {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);
        if(file != null) {
            model.addExistingQuestions(file);
        }
    }

    @FXML
    protected void onMenuClose () {
        stage.close();
        model.checkModify();
    }

    @FXML
    protected void onMenuSave () {
        model.saveAll();
        if (model.getFile() != null)
            stage.setTitle("Flash Card - " + model.getFile().getName());
    }

    @FXML
    protected void onMenuDelete () {
        if(currentQuestion != null) {
            model.delQuestion(currentQuestion);
        }
        currentQuestion = null;
        updateDetailView(null);
    }

    @FXML
    protected void onMenuModify () {
        if(currentQuestion != null) {
            app.addQuestion(1, currentQuestion);
            updateDetailView(list.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    protected void onAddClick () {
        app.addQuestion(0, null);
    }

    @FXML
    protected void onDeleteClick () {
        if(currentQuestion != null) {
            model.delQuestion(currentQuestion);
        }
        currentQuestion = null;
        updateDetailView(null);
    }

    @FXML
    protected void onModifyClick () {
        if(currentQuestion != null) {
            app.addQuestion(1, currentQuestion);
            updateDetailView(list.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    protected void onSmall() {
        answer.setFont(new Font(13));
        question.setFont(new Font(13));
    }
    @FXML
    protected void onLarge() {
        answer.setFont(new Font(24));
        question.setFont(new Font(24));
    }

    @FXML
    protected void onMenuStart() {
        if(model.questionsProperty().size() > 0) {
            stage.close();
            app.startQuiz();
        }
    }

    @FXML
    protected void onMenuLoadFromTxt() {
        if(model.isModify()) {
            app.promptSave(1);
            stage.close();
        }
        else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select txt File");
            File file = fileChooser.showOpenDialog(stage);
            model.loadFromTxt(file);
        }
    }

    public File createFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showSaveDialog(stage);
        return file;
    }

    public void open() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            model.load(file);
            stage.setTitle("Flash Card - " + model.getFile().getName());
        }
    }

    public void newFile() {
        model.delAll();
        model.setFile(null);
        stage.setTitle("Flash Card");
    }

    public void select(Question q) {
        list.getSelectionModel().select(q);
    }

    public void setView(Question newQuestion) {
        question.setText(newQuestion.getQuestion());
        answer.setText(newQuestion.getAnswer());
    }

    public void init(Model model, Stage stage, HelloApplication app) {
        this.app = app;
        this.model = model;
        this.stage = stage;
        this.model.initialController = this;

        this.stage.setOnCloseRequest(e -> {
            model.checkModify();
        });

        list.itemsProperty().bind(model.questionsProperty());
        list.setOnMouseClicked(this::questionView);
        list.setOnKeyPressed(this::questionView);

        if (model.getFile() != null) {
            stage.setTitle("Flash Card - " + model.getFile().getName());
        }

        updateDetailView(null);
    }

    private void updateDetailView(Question q) {
        if(q != null) {
            question.setText(q.getQuestion());
            answer.setText(q.getAnswer());
        }
        else {
            question.setText("");
            answer.setText("");
        }
        currentQuestion = q;
    }

    private void questionView(Event event) {
        Question q = list.getSelectionModel().getSelectedItem();
        updateDetailView(q);
        currentQuestion = q;
    }
}
