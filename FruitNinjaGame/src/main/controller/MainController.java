package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {
    // Tag to link the controller to the view
    // Ex: @FXML private Label label;
    @FXML private Button button;
    @FXML private Label label;


    // Radio Buttons
    @FXML
    private void initialize() {
        
    }

    // Methods to handle events
    // Ex: @FXML
    // private void handleButtonAction(ActionEvent event) {
    // }

    @FXML
    private void handleButtonAction() {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
}