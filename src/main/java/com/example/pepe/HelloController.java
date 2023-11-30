package com.example.pepe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HelloController {


    @FXML
    public Menu exitBtn;

    public BorderPane scenePane;
    Stage stage;
    public void exit(){
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }


}

