package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.controller.MainController;
import main.controller.LongValue;

public class MainApp extends Application {
    private Stage primaryStage;
    private MainController mainController;
    private int secondsElapsed = 0;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Fruit Ninja Game - Progetto di TPS - 2024 - Bertoldini Bonanomi");

        inizializza();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void inizializza() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("guiFolder/mainGui.fxml"));
            StackPane rootLayout = (StackPane) loader.load();

            // Ottieni il controller
            mainController = loader.getController();

            // Passa il riferimento di questa applicazione al controller
            mainController.setMainApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            LongValue lastNanoTime = new LongValue(System.nanoTime());

            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    // Calcola il tempo trascorso
                    double elapsedTime = (now - lastNanoTime.value) / 1000000000.0;
                    lastNanoTime.value = now;

                    // Aggiorna il timer
                    updateTimer(elapsedTime);
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTimer(double elapsedTime) {
        // Aggiorna il timer
        secondsElapsed += (int) elapsedTime;
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;
        String formattedTime = String.format("%02d:%02d", minutes, seconds);
        mainController.updateTimerLabel(formattedTime);
    }
}
