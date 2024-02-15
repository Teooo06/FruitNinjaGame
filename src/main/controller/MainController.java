 package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import main.MainApp;

public class MainController {
    private MainApp mainApp; // Aggiungi una variabile per tenere traccia dell'applicazione principale
    @FXML
    private Button PlayButton;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane anchorPaneIniziale;
    @FXML
    private AnchorPane anchorPaneGioco;
    @FXML
    private Label Timer;

    @FXML
    private void handlePlayButtonAction() {
        // Rimuovi la schermata attuale dallo StackPane
        stackPane.getChildren().clear();
        // Aggiungi la nuova schermata allo StackPane
        stackPane.getChildren().add(anchorPaneGioco);
    }

    // Method to initialize the controller
    public void initialize() {
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