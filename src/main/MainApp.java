package main;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import main.controller.Sprite;

public class MainApp extends Application {
    private int dimX = 1000;
    private int dimY = 600;

    private GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) {
        try {
        primaryStage.setTitle("Fruit Ninja Game - Progetto di TPS - 2024 - Bertoldini Bonanomi");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("guiFolder/mainGui.fxml"));

       
        Canvas canvas = new Canvas(dimX, dimY);
        
        //Group root = new Group(canvas,pane);
        Group root = new Group(canvas);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
 
       

        gc = canvas.getGraphicsContext2D();

        primaryStage.show();




        // Imposto lo sfondo
        Sprite sfondo = new Sprite();
        sfondo.setImage("main/images/background.jpg");
        sfondo.setPosition(0, 0);
        sfondo.setVelocity(0, 0);

        // Mela
        Sprite mela = new Sprite();
        mela.setImage("main/images/apple.png");
        mela.setPosition(300, dimY+100);
        mela.setVelocity(100, 0);
        mela.setVelocitaIniziale(-9); // Range da -12 a -9.5


        // Crea un arraylist per gestire i frutti
        ArrayList<Sprite> elencoFrutta = new ArrayList<Sprite>();


        // nuova animazione
        new AnimationTimer() {
            double lastNanoTime = System.nanoTime();
            
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;
                
                gc.clearRect(0, 0, dimX, dimY);
                sfondo.render(gc);
                
                
                // controllo generatore Frutti
                int genera = (int) (Math.random() * 50);
                
                if (genera == 3) {
                    double tempoIniziale = System.currentTimeMillis();
                    Sprite frutto = new Sprite();
                    frutto.setTempoIniziale(tempoIniziale);
                    frutto.setImageRandom();
                    double px = dimX * Math.random()+50;
                    double py = dimY +100;
                    frutto.setPosition(px, py);
                    // Può andare a destra o a sinistra
                    int i= (int) (Math.random() * 2);
                    if(i==0)
                    frutto.setVelocity(-Math.random()*100, 0);
                    else
                    frutto.setVelocity(Math.random()*100, 0);
                    double velocitaIniziale = -9.5 + (int) (Math.random() * -2.5);
                    frutto.setVelocitaIniziale(velocitaIniziale); // Range da -12 a -9.5
                    elencoFrutta.add(frutto);
                }
                
                //double tempoTrascorso = (System.currentTimeMillis() - tempoIniziale) / 1000.0;


                // movimento frutto
                for (Sprite frutto : elencoFrutta) {
                    frutto.update(elapsedTime);
                    frutto.render(gc);
                }
            }
        }.start();

        } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
