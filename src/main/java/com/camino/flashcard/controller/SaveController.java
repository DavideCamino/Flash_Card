package com.camino.flashcard.controller;

import com.camino.flashcard.model.Model;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class SaveController {

    Model model;
    Stage stage;
    Stage initialWindow;
    int mode;

    @FXML
    protected void onYes() {
        model.saveAll();
        if(!model.isModify()) {
            stage.close();
            if(mode == 1) {
                initialWindow.show();
                model.initialController.open();
            }
            if(mode == 2) {
                initialWindow.show();
                model.initialController.newFile();
            }
        }
    }

    @FXML
    protected void onNo() {
        stage.close();
        if(mode == 1) {
            initialWindow.show();
            model.initialController.open();
        }
        if(mode == 2) {
            initialWindow.show();
            model.initialController.newFile();
        }
    }

    @FXML
    protected void onCancel() {
        initialWindow.show();
        stage.close();
    }

    public void init(Model model, Stage stage, Stage initialWindow, int mode) {
        this.model = model;
        this.stage = stage;
        this.initialWindow = initialWindow;
        this.mode = mode;

        stage.setOnCloseRequest(e -> {
            initialWindow.show();
        });
    }
}
