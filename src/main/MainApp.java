package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.controller.LongValue;
import main.controller.MainController;
import main.controller.Sprite;

public class MainApp extends Application {
    private int dimX = 1000;
    private int dimY = 600;

    private MainController mainController;
    private int secondsElapsed = 0;
    private AnimationTimer timer;
    private long lastNanoTime;
    private boolean running = false;
    private GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) {
        try {
        primaryStage.setTitle("Fruit Ninja Game - Progetto di TPS - 2024 - Bertoldini Bonanomi");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("guiFolder/mainGui.fxml"));

       
        StackPane pane=  (StackPane) loader.load();
        Canvas canvas = new Canvas(dimX, dimY);
        
        //Group root = new Group(canvas,pane);
        Group root = new Group(canvas);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
 
       

        gc = canvas.getGraphicsContext2D();

        primaryStage.show();

        mainController = loader.getController();
        mainController.setMainApp(this);


        LongValue lastNanoTime = new LongValue( System.nanoTime() );

        // Imposto lo sfondo
        Sprite sfondo = new Sprite();
        sfondo.setImage("main/images/background.jpg");
        sfondo.setPosition(0, 0);
        sfondo.setVelocity(0, 0);

        Sprite mela = new Sprite();
        mela.setImage("main/images/apple.png");
        mela.setPosition(dimX / 2, dimY - 80);
        mela.setVelocity(100, -100);
        
        // nuova animazione
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;
                
                gc.clearRect(0, 0, dimX, dimY);
                mela.update(elapsedTime);
                sfondo.render(gc);
                mela.render(gc);
            }
        }.start();


        /*startTimer();
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case P:
                    if (timer != null) {
                        if (running) {
                            stopTimer();
                            running = false;
                        } else {
                            startTimer();
                            running = true;
                        }
                    } else {
                        startTimer();
                        running = true;
                    }
                    break;
                case R:
                    resume();
                    break;
                default:
                    break;
            }
        });*/
        } catch (Exception e) {
        e.printStackTrace();
    }
    }

    /*public void restartTimer() {
        secondsElapsed = 0;
        stopTimer();
        startTimer();
    }

    //public void startTimer() {
        lastNanoTime = System.nanoTime();
        timer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1e9;
                lastNanoTime = currentNanoTime;
                updateTimer(elapsedTime);

                gc.clearRect(0, 0, dimX, dimY);

                mela.update(elapsedTime);
                mela.render(gc);
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
            startTimer();
            running = true;
        }
    }

    private void updateTimer(double elapsedTime) {
        secondsElapsed += elapsedTime;
        int cents = (int) (secondsElapsed / 0.6) % 100;
        int seconds = (int) (secondsElapsed / 60) % 60;
        int minutes = (int) secondsElapsed / 3600;
        String formattedTime = String.format("%02d:%02d:%02d", minutes, seconds, cents);
        mainController.updateTimerLabel(formattedTime);
    }
*/
    public static void main(String[] args) {
        launch(args);
    }
}
