package com.annapolisWorks;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader GUI_Interface = new FXMLLoader();
        GUI_Interface.setLocation(getClass().getResource("/myStage.fxml"));
        Parent root = GUI_Interface.load(getClass().getResource("/myStage.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}

