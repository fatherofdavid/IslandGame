package com.annapolisWorks;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.fxml.FXMLLoader.load;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader GUI_InterfaceLoader = new FXMLLoader();
        GUI_InterfaceLoader.setLocation(getClass().getResource("/intro.fxml"));
        Parent root = load(getClass().getResource("/intro.fxml")); //initialize runs here
        primaryStage.setTitle("Forbidden Island - New Mission");
        primaryStage.setScene(new Scene(root, 544, 360));
        primaryStage.show();

    }

    public static void main(String[] args) {
        /*
        GUI_Controller demoGUI = new GUI_Controller();
        Game mission = new Game();
        mission.newGame(demoGUI);
        */
        launch(args);
    }
}
