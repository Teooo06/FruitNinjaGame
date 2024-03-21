package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import main.controller.Sprite;
import javafx.scene.text.Font;

public class MainApp extends Application {
    private int dimX = 1000;
    private int dimY = 600;
    private int difficolta = 200;

    private GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) {
        try {
        primaryStage.setTitle("Fruit Ninja Game - Progetto di TPS - 2024 - Bertoldini Bonanomi");

        FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(MainApp.class.getResource("guiFolder/mainGui.fxml"));
        Font customFont = loadFont("src/main/fonts/go3v2.ttf", 50);
        
        Canvas canvas = new Canvas(dimX, dimY);
        
        //Group root = new Group(canvas,pane);
        Group root = new Group(canvas);
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        
        gc = canvas.getGraphicsContext2D();
        
        gc.setFont(customFont);

        primaryStage.show();

        mainMenu();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     private Font loadFont(String path, double size) {
        try {
            return Font.loadFont(new FileInputStream(new File(path)), size);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // Gestione dell'eccezione
            return Font.getDefault(); // Ritorna un font predefinito in caso di errore
        }
    }

    private void drawText(String text, double x, double y, Paint color) {
        gc.setFill(color); 
        gc.fillText(text, x, y); // Testo riempito
    }

    public void mainMenu(){
        // Imposto lo sfondo
        Sprite sfondo = new Sprite();
        sfondo.setImage("main/images/mainmenu.png");
        sfondo.setPosition(-2, 0);
        sfondo.setVelocity(0, 0);

        sfondo.render(gc);
        
        // Creo uno sprite che sarà il bottone di gioco
        Sprite playButton = new Sprite();
        playButton.setImage("main/images/classic.png");
        playButton.setPosition(dimX / 2 - 100, dimY / 2 - 50);
        playButton.setVelocity(0, 0);
        playButton.render(gc);

        Sprite playButton2 = new Sprite();
        playButton2.setImage("main/images/watermelonMIN3.png");
        playButton2.setPosition(dimX / 2 - 35, dimY / 2 +15);
        playButton2.setVelocity(0, 0);
        
        playButton2.render(gc);

        
        
        
        // nuova animazione
        new AnimationTimer() {
            double lastNanoTime = System.nanoTime();
            
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;
                
                gc.clearRect(0, 0, dimX, dimY);
                sfondo.render(gc);
                
                // Imposto la rotazione
                playButton2.setRotationAngle(80);
                playButton.setRotationAngle(-80);
                playButton.updateRotation(elapsedTime);
                playButton2.updateRotation(elapsedTime);
                playButton.render(gc);
                playButton2.render(gc); 
                
                drawText("Best \nScore:",dimX/2+ 250, dimY/2 + 50, Color.WHITE);
                drawText("1200",dimX/2+ 250, dimY/2 + 200, Color.WHITE);
                
            }
        }.start();

        
    }


    public void startGame() {

        gc.clearRect(0, 0, dimX, dimY);

        // Imposto lo sfondo
        Sprite sfondo = new Sprite();
        sfondo.setImage("main/images/background.jpg");
        sfondo.setPosition(0, 0);
        sfondo.setVelocity(0, 0);

        // Crea un arraylist per gestire i frutti
        ArrayList<Sprite> elencoFrutta = new ArrayList<Sprite>();

        // Controllo del tempo
        double tempoIniziale = System.currentTimeMillis();

        // nuova animazione
        new AnimationTimer() {
            double lastNanoTime = System.nanoTime();
            
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;
                
                gc.clearRect(0, 0, dimX, dimY);
                sfondo.render(gc);

                // controllo generatore Frutti
                int genera = (int) (Math.random() * difficolta);
                
                if (genera == 3) {
                    double tempoIniziale = System.currentTimeMillis();

                    Sprite frutto = new Sprite();

                    frutto.setTempoIniziale(tempoIniziale);
                    frutto.setImageRandom();
                    // Posizione iniziale
                    // Da 20 a dimx - 20
                    double px = 50 + (int) (Math.random() * (dimX - 50));
                    double py = dimY +100;
                    frutto.setPosition(px, py);
                    // Se la posizione iniziale è a sinistra, la velocità è positiva
                    // Se la posizione iniziale è a destra, la velocità è negativa
                    if (px < dimX / 2) {
                        frutto.setVelocity(100, 0);
                    } else {
                        frutto.setVelocity(-100, 0);
                    }
                    double velocitaIniziale = -10 + (int) (Math.random() * -2.5);
                    frutto.setVelocitaIniziale(velocitaIniziale); // Range da -12 a -9.5

                    // Imposto la rotazione
                    frutto.setRotationAngle((int) (Math.random() * 300)+200);

                    elencoFrutta.add(frutto);
                }
                
                // movimento frutto
                for (Sprite frutto : elencoFrutta) {
                    frutto.update(elapsedTime);
                    frutto.render(gc);
                }
                
                // Controllo e cancello i frutti sotto 200 px dallo schermo
                for (int i = 0; i < elencoFrutta.size(); i++) {
                    if (elencoFrutta.get(i).getPositionY() > dimY + 200) {
                        elencoFrutta.remove(i);
                    }
                }
                // Calcolo del tempo trascorso
                // Calcolo del tempo trascorso
                double tempoDouble = (System.currentTimeMillis() - tempoIniziale) / 1000.0;
                String tempoFormattato = String.format("%.1f", tempoDouble);
                tempoFormattato = tempoFormattato.replace(',', '.'); // Sostituisci la virgola con un punto

                float tempoTrascorso = Float.parseFloat(tempoFormattato);

                // Ogni n secondi aumento la difficoltà e aumento i secondi per aumentare la difficoltà di 40
                if (tempoTrascorso % 60 == 0) { // Ogni minuto (60 secondi)
                    if (difficolta > 30)
                        difficolta -= 5 ;
                }

                // Disegno del testo sul canvas
                gc.setFill(Color.WHITE);
                gc.setFont(javafx.scene.text.Font.font(32)); // Impostazione della dimensione del font
                // Nome del font in base alla difficoltà
                if (difficolta > 120)
                    gc.fillText("Difficoltà Facile", dimX /2 -100, 50); // Testo riempito
                else if (difficolta > 60 && difficolta <= 120)
                    gc.fillText("Difficoltà Media", dimX /2 -100, 50); // Testo riempito
                else
                    gc.fillText("Difficoltà Difficile", dimX /2 -100, 50); // Testo riempito
            }
        }.start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
