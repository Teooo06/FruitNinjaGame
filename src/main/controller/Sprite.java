package main.controller;

import javafx.scene.image.Image;
import main.MainApp;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public class Sprite {
    private Image image;
    // array che prevede più immagini sprite per simulare il movimento
    private boolean termina = false;
    private boolean tagliato = false;
    private boolean bomba = false;
    private String tipoSprite;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double rotationAngle; // Rotation angle in degrees
    private double rotate; // Rotation angle in degrees
    private double width;
    private double height;
    private double opacity = 1.0; // Opacità predefinita
    private int difficolta;
    private int difficoltaBomba;

    // Attributi per la gestione del movimento
    // private double gravity = 9.81;
    private double gravity = 1.62;
    private double velocitaIniziale;
    private double tempoIniziale;

    // Timer distruzioni
    private double tempoDistruzione;

    public Sprite() {
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
    }

    public void setVelocitaIniziale(double velocitaIniziale) {
        this.velocitaIniziale = velocitaIniziale;
    }

    public void setTempoIniziale(double tempoIniziale) {
        this.tempoIniziale = tempoIniziale;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public boolean isTermina() {
        return termina;
    }

    public void setImage(Image i) {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setImage(String filename) {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setPosition(double x, double y) {
        positionX = x;
        positionY = y;
    }

    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = velocitaIniziale;
    }

    public void setRotationAngle(double angleDegrees) {
        this.rotationAngle = angleDegrees;
    }

    public void setRotate(double angleDegrees) {
        this.rotate = angleDegrees;
    }

    public void addVelocity(double x, double y) {
        velocityX += x;
        velocityY += y;
    }

    public void update(double time) {
        positionX += velocityX * time;
        double tempoTrascorso = (System.currentTimeMillis() - tempoIniziale) / 1000.0;
        positionY += calcoloPosLancioY(tempoTrascorso);
        // Rotation
        rotate += rotationAngle * time;
    }

    public void updateRotation(double time) {
        rotate += rotationAngle * time;
    }

    public double calcoloPosLancioY(double time) {
        // calcolo la posizione Y
        double altezza = velocitaIniziale * time + 0.5 * gravity * time * time;
        // System.out.println(" Altezza: " + altezza);
        // Stampa il tempo
        return altezza;
    }

    public void render(GraphicsContext gc) {
        gc.save(); // Save the current state on the stack
        gc.translate(positionX + image.getWidth() / 2, positionY + image.getHeight() / 2); // Translate to the center of
        gc.rotate(rotate); // Rotate around the center of the image
        gc.setGlobalAlpha(opacity);
        gc.drawImage(image, -image.getWidth() / 2, -image.getHeight() / 2); // Draw the image centered at the translated
        gc.setGlobalAlpha(1.0);
        gc.restore(); // Restore the last saved state
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public boolean intersects(double x, double y) {
        return x >= positionX && x <= positionX + width &&
                y >= positionY && y <= positionY + height;
    }

    public String toString() {
        return " Position: [" + positionX + "," + positionY + "]"
                + " Velocity: [" + velocityX + "," + velocityY + "]";
    }

    public void setImageRandom() {

        difficolta = 70; // 70% di probabilità di uscita di un frutto
        difficoltaBomba = 80; // 80% di probabilità di uscita di una bomba non fatale

        // Genera un numero casuale tra 0 e 99
        int num = (int) (Math.random() * 100);
        // Se non è 8
        if (num<8){
            int j = (int) (Math.random() * 100); // da 0 a 99
            if (j < difficolta) {
                int i = (int) (Math.random() * 6); // da 0 a 5
                switch (i) {
                    case 0:
                        setImage("main/images/appleMedium.png");
                        tipoSprite = "apple";
                        break;
                    case 1:
                        setImage("main/images/kiwiMedium.png");
                        tipoSprite = "kiwi";
                        break;
                    case 2:
                        setImage("main/images/lemonMedium.png");
                        tipoSprite = "lemon";
                        break;
                    case 3:
                        setImage("main/images/orangeMedium.png");
                        tipoSprite = "orange";
                        break;
                    case 4:
                        setImage("main/images/pearMedium.png");
                        tipoSprite = "pear";
                        break;
                    case 5:
                        setImage("main/images/pomMedium.png");
                        tipoSprite = "pom";
                        break;
                }
            } else {
                int k = (int) (Math.random() * 100);
                if (k > difficoltaBomba) {
                    setImage("main/images/bombFatalMedium.png");
                    tipoSprite = "bombFatal";
                    bomba = true;
                } else {
                    setImage("main/images/bombTimeMedium.png");
                    tipoSprite = "bombTime";
                    bomba = true;
                }

            }
        } else {
            int h = (int) (Math.random() * 2);
            switch (h) {
                case 0:
                    setImage("main/images/specialFruit1.png");
                    tipoSprite = "specialFruit1";
                    bomba = false;                    
                    break;
                case 1:
                    setImage("main/images/specialFruit2.png");
                    tipoSprite = "specialFruit2";
                    bomba = false;
                    break;
                default:
                    break;
            }
        }


    }

    public void setOnMouseClicked(javafx.event.EventHandler<? super javafx.scene.input.MouseEvent> value) {
    }

    public int tagliato(int punteggio) {

        // Se il frutto è una bomba
        if (tipoSprite.equals("bombFatal") || tipoSprite.equals("bombTime")){
            // Cambia immagine in immagine tagliata
            // TODO: Cambiare immagine in bomba tagliata
            //setImage("main/images/bombCut.png");
            if (tagliato==false) {
                // Musica bomba tagliata
                MainApp.playBomb();
                // Sottrae 50 punti se ce ne sono
                if (punteggio >= 50) {
                    punteggio -= 50;
                } else {
                    punteggio = 0;
                }
                tagliato = true;

                // deve modificare il numero di vite nella mainApp
                if (MainApp.contaVite > 0) {
                    MainApp.contaVite--;
                }

            }
            termina = true;
            return punteggio;
        }
        // Se sono i frutti speciali
        if (bomba == false && (tipoSprite.equals("specialFruit1") || tipoSprite.equals("specialFruit2"))){
            if(tagliato==false){
                // Aggiunge 10 punti
                punteggio += 50;
                tagliato = true;
                // Cambia immagine in immagine tagliata
                String nome = "main/images/" + tipoSprite + "Split.png";
                // creo un'altro sprite per lo splash
                Sprite splash = new Sprite();
                splash.setImageSplash();
                splash.setPosition(positionX, positionY);
                splash.setTempoIniziale(System.currentTimeMillis());
                splash.setTempoDistruzione(7);
                splash.setOpacity(0.5);
                // Ruoto lo splash
                splash.setRotate((int) (Math.random() * 360));
                // Aggiungo lo splash alla lista
                MainApp.elencoSplash.add(splash);
                // Riproduco il suono una volta playFruit();
                MainApp.playFruit();
                setImage(nome);
                termina = true;
                return punteggio;
            }
        }

        // Cambia immagine in immagine tagliata
        if (tagliato==false) {
            // Aggiunge 10 punti
            punteggio += 10;
            tagliato = true;
            // Cambia immagine in immagine tagliata
            String nome = "main/images/" + tipoSprite + "SplitMedium.png";
            // creo un'altro sprite per lo splash
            Sprite splash = new Sprite();
            splash.setImageSplash();
            splash.setPosition(positionX, positionY);
            splash.setTempoIniziale(System.currentTimeMillis());
            splash.setTempoDistruzione(7);
            splash.setOpacity(0.5);
            // Ruoto lo splash
            splash.setRotate((int) (Math.random() * 360));
            // Aggiungo lo splash alla lista
            MainApp.elencoSplash.add(splash);
            // Riproduco il suono una volta playFruit();
            MainApp.playFruit();
            setImage(nome);
        }
        termina = true;
        return punteggio;
    }

    // Metodo is tagliato
    public boolean isTagliato() {
        return tagliato;
    }
    
    // Metodo is bomba
    public boolean isBomba() {
        return bomba;
    }

    public void setImageSplash() {
        // Imposto l'immagine splash tra le 3
        int i = (int) (Math.random() * 3); // da 0 a 2
        switch (i) {
            case 0:
                setImage("main/images/colorSplash1.png");
                break;
            case 1:
                setImage("main/images/colorSplash2.png");
                break;
            case 2:
                setImage("main/images/colorSplash3.png");
                break;
            default:
                setImage("main/images/colorSplash1.png");
        }
    }

    public void setTempoDistruzione(double tempoDistruzione) {
        this.tempoDistruzione = tempoDistruzione;
    }

    public double getTempoDistruzione() {
        return tempoDistruzione;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public double getOpacity() {
        return opacity;
    } 

    public double getTempoIniziale() {
        return tempoIniziale;
    }

}
