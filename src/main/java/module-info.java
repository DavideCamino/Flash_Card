module com.camino.flashcard {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.camino.flashcard to javafx.fxml;
    exports com.camino.flashcard;
    opens com.camino.flashcard.controller to javafx.fxml;
    exports com.camino.flashcard.controller;
}