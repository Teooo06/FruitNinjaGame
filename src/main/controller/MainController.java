package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import main.MainApp;

public class MainController {
    @FXML private Button PlayButton;
    @FXML private StackPane stackPane;
    @FXML private AnchorPane anchorPaneIniziale;
    @FXML private AnchorPane anchorPaneGioco;
    @FXML private Label Timer;

    private MainApp mainApp; // Dichiarazione della variabile mainApp

    @FXML
    private void handlePlayButtonAction() {
        // Rimuovi la schermata attuale dallo StackPane
        stackPane.getChildren().clear();
        // Aggiungi la nuova schermata allo StackPane
        stackPane.getChildren().add(anchorPaneGioco);
        // Start timer
        mainApp.startTimer(); // Chiamata a startTimer() sull'istanza di MainApp

    }

    // Method to initialize the controller
    public void initialize() {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(anchorPaneIniziale);
        // Timer.setText("00:00");
        Timer.setText("00:00");
        // Start timer

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void updateTimerLabel(String formattedTime) {
        Timer.setText(formattedTime);
    }
}