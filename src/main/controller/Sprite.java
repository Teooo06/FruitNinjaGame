package main.controller;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public class Sprite
{
    private Image image;
    //array che prevede più immagini sprite per simulare il movimento
    private boolean termina=false;
    private double positionX;
    private double positionY;    
    private double velocityX;
    private double velocityY;
    private double rotationAngle; // Rotation angle in degrees
    private double rotate; // Rotation angle in degrees
    private double width;
    private double height;
    private int difficolta;
    private int difficoltaBomba;


    // Attributi per la gestione del movimento
    private double gravity = 9.81;
    private double velocitaIniziale;
    private double tempoIniziale;

    public Sprite(){
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
    
    public void setImage(Image i)
    {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }   

    public void setImage(String filename)
    {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setPosition(double x, double y)
    {
        positionX = x;
        positionY = y;
    }

    public void setVelocity(double x, double y)
    {
        velocityX = x;
        velocityY = velocitaIniziale;
    }

    public void setRotationAngle(double angleDegrees) {
        this.rotationAngle = angleDegrees;
    }

    public void addVelocity(double x, double y)
    {
        velocityX += x;
        velocityY += y;
    }

    public void update(double time)
    {   
        positionX += velocityX * time;
        double tempoTrascorso = (System.currentTimeMillis() - tempoIniziale) / 1000.0;
        positionY += calcoloPosLancioY(tempoTrascorso);
        // Rotation
        rotate += rotationAngle * time;
    }

    public void updateRotation(double time)
    {
        rotate += rotationAngle * time;
    }

    public double calcoloPosLancioY(double time){
        // calcolo la posizione Y
        double altezza = velocitaIniziale * time + 0.5 * gravity * time * time;
        //System.out.println(" Altezza: " + altezza);
        // Stampa il tempo
        return altezza;
    }

    public void render(GraphicsContext gc)
    {   
        gc.save(); // Save the current state on the stack
        gc.translate(positionX + image.getWidth() / 2, positionY + image.getHeight() / 2); // Translate to the center of
        gc.rotate(rotate); // Rotate around the center of the image
        gc.drawImage(image, -image.getWidth() / 2, -image.getHeight() / 2); // Draw the image centered at the translated
        gc.restore(); // Restore the last saved state
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(positionX,positionY,width,height);
    }

    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }

    public boolean intersects(double x, double y)
    {
        return x >= positionX && x <= positionX + width && 
               y >= positionY && y <= positionY + height;
    }
    
    public String toString()
    {
        return " Position: [" + positionX + "," + positionY + "]" 
        + " Velocity: [" + velocityX + "," + velocityY + "]";
    }

    public void setImageRandom() {

        difficolta = 70; // 70% di probabilità di uscita di un frutto
        difficoltaBomba = 80; // 80% di probabilità di uscita di una bomba non fatale

        int j = (int) (Math.random() * 100);
        if(j<difficolta){
                int i = (int) (Math.random() * 5);
                switch (i) {
                    case 0:
                        setImage("main/images/apple.png");
                        break;
                    case 1:
                        setImage("main/images/kiwi.png");
                        break;
                    case 2:
                        setImage("main/images/lemon.png");
                        break;
                    case 3:
                        setImage("main/images/orange.png");
                        break;
                    case 4:
                        setImage("main/images/pear.png");
                        break;
                    case 5:
                        setImage("main/images/pom.png");
                        break;
                    }
        }
        else{
                int k = (int) (Math.random() * 100);
                if (k>difficoltaBomba) {
                    setImage("main/images/bombFatal.png");
                }else{
                    setImage("main/images/bombTime.png");
                }       
              
        }

    }
}
