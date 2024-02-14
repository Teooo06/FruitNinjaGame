package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application{
    private Stage primaryStage;

    //private MainController mainController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        
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

            //MainController controller=loader.getController();            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
