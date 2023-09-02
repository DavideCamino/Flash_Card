package com.camino.flashcard.model;

import com.camino.flashcard.HelloApplication;
import com.camino.flashcard.controller.InitialController;
import com.camino.flashcard.controller.QuizController;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Model {
    ListProperty<Question> questions;
    ObservableList<Question> questionsContent;
    File file;
    Boolean modify = false;
    HelloApplication app;
    public InitialController initialController;
    public QuizController quizController;
    private ArrayList<Question> currentQuestions;
    private ArrayList<Question> currentWrong;
    int correct = 0;
    int totQuestion = 0;

    public Model (HelloApplication app) {
        this.questionsContent = FXCollections.observableList(new ArrayList<>());
        this.questions = new SimpleListProperty<>();
        this.questions.set(questionsContent);
        this.app = app;
        this.file = null;
        this.currentQuestions = new ArrayList<>();
        this.currentWrong = new ArrayList<>();
    }

    public void load(File file) {
        this.file = file;
        delAll();
        addAll(file);
        modify = false;
    }

    public void delAll() {
        questionsContent.removeIf(q -> true);
    }

    public void delQuestion(Question q) {
        questionsContent.remove(q);
        totQuestion--;
        modify = true;
    }

    public void addExistingQuestions(File file) {
        addAll(file);
        modify = true;
    }

    public void saveAll() {
        if(file == null) {
            file = initialController.createFile();
        }
        if(file != null) {
            try {
                List<Question> list = new ArrayList<>(questionsContent.size());
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                list.addAll(questionsContent);
                oos.writeObject(list);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            modify = false;
        }
    }

    public ListProperty<Question> questionsProperty() {
        return questions;
    }

    public void addQuestion(Question q) {
        if(!questionsContent.contains(q)) {
            questionsContent.add(q);
            modify = true;
        }
    }

    public void modifyQuestion(Question oldQuestion, Question newQuestion, int mode) {
        if(!questionsContent.contains(newQuestion) || newQuestion.equals(oldQuestion)) {
            questionsContent.set(questionsContent.indexOf(oldQuestion), newQuestion);
            modify = true;
        }
        if(mode == 0) {
            initialController.setView(newQuestion);
            initialController.select(newQuestion);
        }
        if(mode == 1) {
            quizController.setView(newQuestion);
        }
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void checkModify() {
        if(modify) {
            app.promptSave(0);
        }
    }

    public boolean isModify() {
        return modify;
    }

    public void initQuiz() {
        currentQuestions.removeIf(q -> true);
        currentQuestions.addAll(questionsContent);
        correct = 0;
        totQuestion = questionsContent.size();
    }

    public void initQuizWrong() {
        currentQuestions.removeIf(q -> true);
        currentQuestions.addAll(currentWrong);
        correct = 0;
        totQuestion = currentWrong.size();
        currentWrong.removeIf(q -> true);
    }

    public Question getQuestion() {
        if(currentQuestions.size() > 0) {
            int pos = new Random().nextInt(currentQuestions.size());
            Question q = currentQuestions.get(pos);
            currentQuestions.remove(pos);
            return q;
        }
        else {
            return new Question("Quiz termintao, puntegio: " + correct + "/" + totQuestion, "");
        }
    }

    public void addWrong(Question q) {
        currentWrong.add(q);
    }

    public void incCorrect(Question q) {
        if(!q.getAnswer().equals("")) {
            correct++;
        }
    }

    public void loadFromTxt(File file) {
        try {
            Scanner txt = new Scanner(file);
            String text = "";
            String [] vector;
            while (txt.hasNext()) {
                text = text.concat(txt.nextLine()).concat("\n");
            }
            vector = text.split("#\n");
            for(int i = 0; i < vector.length; i+=2) {
                if(i < vector.length - 1) {
                    vector[i] = vector[i].substring(0, vector[i].length() - 1);
                    vector[i + 1] = vector[i + 1].substring(0, vector[i + 1].length() - 1);
                    Question q = new Question(vector[i], vector[i + 1]);
                    addQuestion(q);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addAll(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object input = ois.readObject();
            try {
                List<Question> list = (List<Question>) input;
                for (Question q : list) {
                    if(!questionsContent.contains(q)) {
                        questionsContent.add(q);
                    }
                }
            } catch (Exception e) {
                System.err.println("file corrotto");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
