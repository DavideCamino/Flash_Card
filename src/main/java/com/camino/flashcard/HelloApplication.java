package com.camino.flashcard;

import com.camino.flashcard.controller.AddQuestionController;
import com.camino.flashcard.controller.InitialController;
import com.camino.flashcard.controller.QuizController;
import com.camino.flashcard.controller.SaveController;
import com.camino.flashcard.model.Model;
import com.camino.flashcard.model.Question;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    Model model;
    Stage initialWindow;
    Stage addWindow;

    @Override
    public void start(Stage stage) throws IOException {
        model = new Model(this);
        initialWindow();
    }

    public static void main(String[] args) {
        launch();
    }

    public void initialWindow() {
        try {
            initialWindow = new Stage();
            FXMLLoader initial = new FXMLLoader(HelloApplication.class.getResource("initial.fxml"));
            Scene scene = new Scene(initial.load());
            initialWindow.setScene(scene);
            InitialController initialController = initial.getController();
            initialWindow.setTitle("Flash Card");
            initialController.init(model, initialWindow, this);
            initialWindow.show();
        } catch (Exception e) {
            System.err.println();
            throw new RuntimeException(e);
        }
    }

    public void addQuestion(int mode, Question q) {
        if(addWindow == null || !addWindow.isShowing()) {
            try {
                addWindow = new Stage();
                FXMLLoader addQuestion = new FXMLLoader(HelloApplication.class.getResource("add-question.fxml"));
                Scene scene = new Scene(addQuestion.load());
                addWindow.setScene(scene);
                AddQuestionController initialController = addQuestion.getController();
                initialController.init(model, addWindow, this, mode, q);
                addWindow.show();
            } catch (Exception e) {
                System.err.println();
                throw new RuntimeException(e);
            }
        }
        else if(addWindow.isIconified()) {
            addWindow.setIconified(false);
        }
        else {
            addWindow.setAlwaysOnTop(true);
            addWindow.setAlwaysOnTop(false);
        }
    }

    public void promptSave(int mode) {
        try {
            Stage stage = new Stage();
            FXMLLoader save = new FXMLLoader(HelloApplication.class.getResource("save.fxml"));
            Scene scene = new Scene(save.load());
            stage.setScene(scene);
            stage.setTitle("Add Question");
            SaveController saveController = save.getController();
            saveController.init(model, stage, initialWindow, mode);
            stage.show();
        } catch (Exception e) {
            System.err.println();
            throw new RuntimeException(e);
        }
    }

    public void startQuiz() {
        try {
            Stage stage = new Stage();
            FXMLLoader quiz = new FXMLLoader(HelloApplication.class.getResource("quiz.fxml"));
            Scene scene = new Scene(quiz.load());
            stage.setScene(scene);
            QuizController initialController = quiz.getController();
            stage.setTitle("Quiz");
            initialController.init(stage, this, model);
            stage.show();
        } catch (Exception e) {
            System.err.println();
            throw new RuntimeException(e);
        }
    }
}