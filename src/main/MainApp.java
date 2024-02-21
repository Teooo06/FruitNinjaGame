package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.controller.MainController;
//import main.controller.LongValue;

public class MainApp extends Application {
    private Stage primaryStage;
    private MainController mainController;
    private int secondsElapsed = 0;
    private AnimationTimer timer;
    private long lastNanoTime; // Memorizza lastNanoTime come variabile di istanza
    private boolean running = false;

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

            mainController = loader.getController(); // Utilizza l'istanza ottenuta dal FXMLLoader

            // Passa il riferimento di questa applicazione al controller
            mainController.setMainApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            

            // Se il timer è già attivo, fermalo
            if (timer != null) {
                timer.stop();
            }

            // Se premuto il tasto "p" della tastiera, il timer si ferma
            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case P:
                        if (timer != null) {
                            if(running) {
                                stopTimer();
                                running = false;
                            } else {
                                restartTimer();
                                running = true;
                            }
                        } else {
                            startTimer();
                        }
                        break;
                    case R:
                        resume();
                        break;
                    default:
                        break;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restartTimer() {
        secondsElapsed = 0;
        stopTimer();
        startTimer();
    }

    public void startTimer() {
        lastNanoTime = System.nanoTime();
        if (timer != null) {
            timer.stop();
        }
        timer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastNanoTime) / 10000000;
                lastNanoTime = currentNanoTime;
                updateTimer(elapsedTime);
            }
        };
        timer.start();
    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    public void resume() {
        if (!running) {
            timer.start();
            running = true;
        }
    }

    private void updateTimer(double elapsedTime) {
        secondsElapsed += elapsedTime;
        int cents = (int) (secondsElapsed / 0.6 ) % 100;
        int seconds = (int) (secondsElapsed / 60) % 60;
        int minutes = (int) secondsElapsed / 3600;
        String formattedTime = String.format("%02d:%02d:%02d", minutes, seconds, cents);
        mainController.updateTimerLabel(formattedTime);
    }

}
