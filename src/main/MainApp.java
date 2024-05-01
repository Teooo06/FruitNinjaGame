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
import main.files.LeggiPunti;
import main.files.ScriviPunti;
import javafx.scene.text.Font;

public class MainApp extends Application {
    private int dimX = 1000;
    private int dimY = 600;
    private int difficolta = 200;
    private boolean gameStarted = false;
    private int score = 0;
    public static int contaVite = 3;
    Font customFont = loadFont("src/main/fonts/go3v2.ttf", 50);
    Font customFont2 = loadFont("src/main/fonts/go3v2.ttf", 30);
    Font customFont3 = loadFont("src/main/fonts/go3v2.ttf", 80);
    Font customFont4 = loadFont("src/main/fonts/go3v2.ttf", 20);
    private AnimationTimer mainMenuTimer;
    private AnimationTimer gameTimer;
    private AnimationTimer gameOverTimer;
    private AnimationTimer infoMenuTimer;
    
    // Best score
    private int BestScore = 0;
    LeggiPunti leggiPunti = new LeggiPunti();
    ScriviPunti scriviPunti = new ScriviPunti();

    private GraphicsContext gc;

    // Varibili booleane per controllare le schermate di gioco
    private boolean mainMenu = true;
    private boolean game = false;
    private boolean gameOver = false;
    private boolean infoMenu = false;

    @Override
    public void start(Stage primaryStage) {
        try {
        primaryStage.setTitle("Fruit Ninja Game - Progetto di TPS - 2024 - Bertoldini Bonanomi");
        primaryStage.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(MainApp.class.getResource("guiFolder/mainGui.fxml"));
        
        Canvas canvas = new Canvas(dimX, dimY);
        
        //Group root = new Group(canvas,pane);
        Group root = new Group(canvas);
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        
        gc = canvas.getGraphicsContext2D();
        
        gc.setFont(customFont);

        primaryStage.show();

        // Leggo il punteggio migliore
        BestScore = leggiPunti.leggiPunti();
        System.out.println("Best score: " + BestScore);


        mainMenu(scene);

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

    public void mainMenu(Scene scene){
        // Disattivo tutte le altre schermate
        mainMenu = true;
        game = false;
        gameOver = false;
        infoMenu = false;

        // Controllo se sono iniziati i timer e li fermo
        if (mainMenuTimer != null) {
            mainMenuTimer.stop();
        }
        if (gameTimer != null) {
            gameTimer.stop();
        }
        if (gameOverTimer != null) {
            gameOverTimer.stop();
        }

        // Imposto lo sfondo
        Sprite sfondo = new Sprite();
        sfondo.setImage("main/images/mainmenu.png");
        sfondo.setPosition(-2, 0);
        sfondo.setVelocity(0, 0);

        sfondo.render(gc);

        // Dimensioni immagine + grande:  255 × 261 px (posizione dimX/2 - 100, dimY/2 - 50)
        
        // Creo uno sprite che sarà il bottone di gioco (due bottoni uno dentro l'altro per l'effetto 3D)
        Sprite playButton = new Sprite();
        playButton.setImage("main/images/classic.png");
        playButton.setPosition(dimX / 2 - 100, dimY / 2 - 50);
        playButton.setVelocity(0, 0);
        playButton.setRotationAngle(-80);
        playButton.render(gc);

        Sprite playButton2 = new Sprite();
        playButton2.setImage("main/images/watermelonMIN3.png");
        playButton2.setPosition(dimX / 2 - 35, dimY / 2 +15);
        playButton2.setVelocity(0, 0);
        playButton2.setRotationAngle(80);
        playButton2.render(gc);

        Sprite infoButton = new Sprite();
        infoButton.setImage("main/images/infoButton.png");
        infoButton.setPosition(80, dimY / 2 + 50);
        infoButton.setVelocity(0, 0);
        infoButton.setRotationAngle(80);
        infoButton.render(gc);

        Sprite infoButton2 = new Sprite();
        infoButton2.setImage("main/images/orangeMedium.png");
        infoButton2.setPosition(125, dimY / 2 + 95);
        infoButton2.setVelocity(0, 0);
        infoButton2.setRotationAngle(-80);
        infoButton2.render(gc);

        // nuova animazione
        mainMenuTimer = new AnimationTimer() {
            double lastNanoTime = System.nanoTime();
            
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;

                // Controllo se il mouse è sopra il pulsante
                scene.setOnMouseMoved(e -> {
                    double x = e.getSceneX();
                    double y = e.getSceneY();
                    if (x >= dimX / 2 - 100 && x <= dimX / 2 + 155 && y >= dimY / 2 - 50 && y <= dimY / 2 + 211){ 
                        // Imposto la rotazione a 0
                        playButton.setRotationAngle(0);
                    } else {
                        // Imposto la rotazione
                        playButton.setRotationAngle(-80);
                    }
                    // Info image è grande 200 200
                    if (x >= 80 && x <= 280 && y >= dimY / 2 + 50 && y <= dimY / 2 + 250){ 
                        // Imposto la rotazione a 0
                        infoButton.setRotationAngle(0);
                    } else {
                        // Imposto la rotazione
                        infoButton.setRotationAngle(80);
                    }
                });
            
                // Controllo se il mouse è sopra il pulsante
                scene.setOnMouseClicked(e -> {
                    double x = e.getSceneX();
                    double y = e.getSceneY();
                    if( x >= dimX / 2 - 100 && x <= dimX / 2 + 155 && y >= dimY / 2 - 50 && y <= dimY / 2 + 211 && !gameStarted && mainMenu){
                        gc.clearRect(0, 0, dimX, dimY);
                        // Imposto le vite e il punteggio a 0 e difficolta a 200
                        contaVite = 3;
                        score = 0;
                        difficolta = 200;
                        startGame(scene);
                    }
                    if( x >= 80 && x <= 280 && y >= dimY / 2 + 50 && y <= dimY / 2 + 250 && !gameStarted && mainMenu){
                        gc.clearRect(0, 0, dimX, dimY);
                        infoMenu(scene);
                    }
                });
                
                gc.clearRect(0, 0, dimX, dimY);
                sfondo.render(gc);
                
                // Imposto la rotazione
                playButton.updateRotation(elapsedTime);
                playButton2.updateRotation(elapsedTime);
                playButton.render(gc);
                playButton2.render(gc); 
                infoButton.updateRotation(elapsedTime);
                infoButton2.updateRotation(elapsedTime);
                infoButton.render(gc);
                infoButton2.render(gc);
                
                gc.setFont(customFont);
                drawText("Best \nScore:",dimX/2+ 250, dimY/2 + 50, Color.WHITE);
                drawText(String.valueOf(BestScore) ,dimX/2+ 250, dimY/2 + 175, Color.WHITE);
                
            }
        };
        mainMenuTimer.start();
    }

    public void infoMenu(Scene scene){
        // Disattivo tutte le altre schermate
        mainMenu = false;
        game = false;
        gameOver = false;
        infoMenu = true;

        if(mainMenuTimer != null){
            mainMenuTimer.stop();
        }
        // Spiegare cosa valgono i vari frutti e le bombe
        gc.clearRect(0, 0, dimX, dimY);

        Sprite sfondo = new Sprite();
        sfondo.setImage("main/images/infoMenuMedium.png");
        sfondo.setPosition(0, 0);
        sfondo.setVelocity(0, 0);
        sfondo.render(gc);
        
        // Agggiungo delle foto di fianco a ogni spiegazione
        Sprite frutto = new Sprite();
        frutto.setImage("main/images/appleMedium.png");
        frutto.setPosition(dimX-350, 185);
        frutto.setRotationAngle(80);

        Sprite bomba = new Sprite();
        bomba.setImage("main/images/bombFatalMedium.png");
        bomba.setPosition(dimX-200, 275);
        bomba.setRotationAngle(80);

        Sprite vite = new Sprite();
        vite.setImage("main/images/lives3.png");
        vite.setPosition(dimX-275, 400);

        Sprite back = new Sprite();
        back.setImage("main/images/BackMin.png");
        back.setPosition(30, 20);


        // Animazione loop
        infoMenuTimer =new AnimationTimer() {
            double lastNanoTime = System.nanoTime();

            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;
                gc.clearRect(0, 0, dimX, dimY);

                // Disegna lo sfondo
                sfondo.render(gc);

                // Scritta
                gc.setFont(customFont3);
                drawText("Info", dimX / 2 - 100, 80, Color.WHITE);
                gc.setFont(customFont2);
                drawText("Fruit Ninja è un gioco in cui bisogna tagliare i frutti \nper vincere... ma attento alle bombe!", 80, 125,
                Color.WHITE);
                drawText("Ogni frutto tagliato vale 10 punti,", 75, 250,
                Color.WHITE);
                drawText("ogni bomba tagliata toglie 100 punti,", 75, 350, Color.WHITE);
                gc.setFont(customFont4);
                drawText("Se un frutto cade perdi una vita", 75, 275, Color.WHITE);
                drawText("e una vita.", 75, 375, Color.WHITE);
                gc.setFont(customFont2);
                drawText("Il gioco finisce quando le vite sono 0.", 75, 450, Color.WHITE);
                drawText("Premi 'P' per tornare al menu", 75, dimY-50, Color.WHITE);


                frutto.updateRotation(elapsedTime);
                frutto.render(gc);
                bomba.updateRotation(elapsedTime);
                bomba.render(gc);
                vite.render(gc);
                back.render(gc);

                // Gestione input
                scene.setOnKeyPressed(e -> {
                    if (e.getText().equals("p") && infoMenu) {
                        mainMenu(scene);
                    }
                });

                // Controllo se si clicca il pulsante back
                scene.setOnMouseClicked(e -> {
                    double x = e.getSceneX();
                    double y = e.getSceneY();
                    if (x >= 30 && x <= 130 && y >= 20 && y <= 120 && !gameStarted && infoMenu) {
                        // Cambio schermata e ritorno a quella principale
                        mainMenu(scene);
                    }
                });
            }
        };
        infoMenuTimer.start();
        
    }
    
    
    public void startGame(Scene scene) {
        // Disattivo tutte le altre schermate
        mainMenu = false;
        game = true;
        gameOver = false;
        infoMenu = false;

        if (mainMenuTimer != null){
            mainMenuTimer.stop();
        }
        gameStarted = true;
        
        gc.clearRect(0, 0, dimX, dimY);
        
        Sprite sfondo = new Sprite();
        Sprite vite = new Sprite();
        // Imposto lo sfondo
        sfondo.setImage("main/images/backgroundDIM.jpg");
        sfondo.setPosition(0, 0);
        sfondo.setVelocity(0, 0);
        
        // Imposto lo sprite per le 3 vite
        vite.setImage("main/images/lives3.png");
        vite.setPosition(dimX - 130, 10);
        vite.setVelocity(0, 0);
        
        // Crea un arraylist per gestire i frutti
        ArrayList<Sprite> elencoFrutta = new ArrayList<Sprite>();
        
        // Controllo del tempo
        double tempoIniziale = System.currentTimeMillis();
        
        // nuova animazione
        gameTimer = new AnimationTimer() {
            double lastNanoTime = System.nanoTime();
            
            public void handle(long currentNanoTime) {
                /*
                // Se il gioco è in pausa non fare nulla
                if (!gameStarted) {
                    return;
                } */
                
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;
                // Controllo se il gioco è finito
                if (contaVite == 0) {
                    // Blocchiamo il gioco
                    gameStarted = false;
            
                    // Mostro il game over
                    gc.clearRect(0, 0, dimX, dimY);
                    gameOver(scene);
                }
                
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
                    // Da 50 a dimX - 50
                    double px = 50 + (int) (Math.random() * (dimX - 50));
                    double py = dimY + 100;
                    frutto.setPosition(px, py);
                    // Se la posizione iniziale è a sinistra, la velocità è positiva
                    // Se la posizione iniziale è a destra, la velocità è negativa
                    if (px < dimX / 2) {
                        frutto.setVelocity(100, 0);
                    } else {
                        frutto.setVelocity(-100, 0);
                    }
                    double velocitaIniziale = -3.6 + (int) (Math.random() * 0.7);
                    frutto.setVelocitaIniziale(velocitaIniziale); // Range da -3.6 a -2.9
                    
                    // Imposto la rotazione da -400 a 400
                    frutto.setRotationAngle(-400 + (int) (Math.random() * 800));
                    
                    elencoFrutta.add(frutto);
                }
                
                // movimento frutto
                for (Sprite frutto : elencoFrutta) {
                    frutto.update(elapsedTime);
                    frutto.render(gc);
                }
                
                // Controllo e cancello i frutti sotto 200 px dallo schermo
                for (int i = 0; i < elencoFrutta.size(); i++) {
                    // Stampa frutti sotto ai 200 px
                    if (elencoFrutta.get(i).getPositionY() > dimY + 200) {
                        // Controllo se il frutto non è stato tagliato e se non è una bomba tolgo una vita
                        if (!elencoFrutta.get(i).isTagliato() && !elencoFrutta.get(i).isBomba()) {
                            if (MainApp.contaVite > 0) {
                                contaVite--;
                            }
                        }
                        elencoFrutta.remove(i);
                    }
                }

                // Controllo se il mouse è sopra un frutto metre è premuto
                scene.setOnMouseDragged(e->{
                            double x = e.getSceneX();
                            double y = e.getSceneY();
                            for (int i = 0; i < elencoFrutta.size(); i++) {
                                if (elencoFrutta.get(i).intersects(x, y) && game) {
                                    score = elencoFrutta.get(i).tagliato(score);
                                }
                            }
                });

                // Calcolo del tempo trascorso
                double tempoDouble = (System.currentTimeMillis() - tempoIniziale) / 1000.0;
                String tempoFormattato = String.format("%.1f", tempoDouble);
                tempoFormattato = tempoFormattato.replace(',', '.'); // Sostituisci la virgola con un punto
                
                float tempoTrascorso = Float.parseFloat(tempoFormattato);
                
                // Ogni n secondi aumento la difficoltà e aumento i secondi per aumentare la difficoltà di 40
                if (tempoTrascorso % 60 == 0) { // Ogni minuto (60 secondi)
                    if (difficolta > 30)
                    difficolta -= 5 ;
                    //System.out.println("Difficoltà: " + difficolta);
                }
                
                // Imposto x e y per il testo
                int x = dimX/2 - 200;
                int y = dimY - 10;
                gc.setFont(customFont);
                // Nome del font in base alla difficoltà
                if (difficolta > 120)
                drawText("Difficoltà Facile", x, y, Color.WHITE); // Testo riempito
                else if (difficolta > 60 && difficolta <= 120)
                drawText("Difficoltà Media", x, y, Color.WHITE); // Testo riempito
                else
                drawText("Difficoltà Difficile", x, y, Color.WHITE); // Testo riempito
                
                // Mostro il punteggio
                gc.setFont(customFont2);
                drawText( "Score: " + score, 30, 50, Color.WHITE); // Testo riempito
                

                vite.setImage("main/images/lives" + contaVite + ".png");
                vite.render(gc);
            }
        };
        gameTimer.start();
    }

    public void gameOver(Scene scene) {

        // Disattivo tutte le altre schermate
        mainMenu = false;
        game = false;
        gameOver = true;
        infoMenu = false;

        if (gameTimer != null) {
            gameTimer.stop();
        }
        // Spiegare cosa valgono i vari frutti e le bombe
        gc.clearRect(0, 0, dimX, dimY);

        Sprite sfondo = new Sprite();
        sfondo.setImage("main/images/gameOverBackgroundBig.png");
        sfondo.setPosition(0, 0);
        sfondo.setVelocity(0, 0);
        sfondo.render(gc);

        // Animazione loop
        gameOverTimer = new AnimationTimer() {

            public void handle(long currentNanoTime) {
                gc.clearRect(0, 0, dimX, dimY);

                // Disegna lo sfondo
                sfondo.render(gc);

                // Scritta
                gc.setFont(customFont);
                drawText(("Punteggio: "+score), dimX / 2 - 140, dimY -170, Color.RED);
                gc.setFont(customFont2);
                drawText("Premi il tasto 'P' per tornare al menu", dimX/2 -250, dimY -130, Color.RED);

                // Se il punteggio è maggiore del best score lo salvo nel file
                if (score > BestScore) {
                    BestScore = score;
                    scriviPunti.scriviUtenti(BestScore);
                }

                // Gestione input
                scene.setOnKeyPressed(e -> {
                    if (e.getText().equals("p") && !gameStarted && gameOver) {
                        mainMenu(scene);
                    }
                });
                
            }
        };
        gameOverTimer.start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
