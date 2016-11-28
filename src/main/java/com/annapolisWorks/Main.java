package com.annapolisWorks;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader GUI_InterfaceLoader = new FXMLLoader();
        GUI_InterfaceLoader.setLocation(getClass().getResource("/myStage.fxml"));
        Parent root = GUI_InterfaceLoader.load(getClass().getResource("/myStage.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
<<<<<<< HEAD
        launch(args);
=======
        GUI_Controller demoGUI = new GUI_Controller();
        Game mission = new Game();
        mission.newGame(demoGUI);
        //launch(args);
>>>>>>> df82c19705a31d396212dae2a6c411fe3898aed6
    }
}
