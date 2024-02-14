package main;



//import esempio.model.Rubrica;
//import esempio.ui.MainController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application{
    private Stage primaryStage;

    //private MainController mainController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Demo JavaFX Badoni");

        inizializza();
        
    }
    
     public static void main(String[] args) {
        launch(args);
    }

    private void inizializza() {
        
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("guiFolder/mainGui.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();

            //MainController controller=loader.getController();            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
