package com.annapolisWorks;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Intro_Controller implements Initializable{


    @FXML
    private ChoiceBox<String> player1ChoiceBox;

    @FXML
    private ChoiceBox<String> player2ChoiceBox;

    @FXML
    private ChoiceBox<String> player3ChoiceBox;

    @FXML
    private ChoiceBox<String> player4ChoiceBox;

    @FXML
    private RadioButton difficultyNoviceRadio;

    @FXML
    private ToggleGroup difficulty;

    @FXML
    private RadioButton difficultyExperiencedRadio;

    @FXML
    private RadioButton difficultyLegendaryRadio;


    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Button startButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        player1ChoiceBox.setItems(adventurers);
        player1ChoiceBox.setValue(adventurers.get(1));
        player2ChoiceBox.setItems(adventurers);
        player2ChoiceBox.setValue(adventurers.get(2));
        player3ChoiceBox.setItems(adventurers);
        player3ChoiceBox.setValue(adventurers.get(0));
        player4ChoiceBox.setItems(adventurers);
        player4ChoiceBox.setValue(adventurers.get(0));

        //hefty code can be added to force no duplicate choices and at least 2 players
    }

    ObservableList<String> adventurers = FXCollections.observableArrayList("not selected", "Explorer", "Pilot",
            "Engineer", "Messenger", "Navigator", "Diver");

    @FXML
    void startUp(ActionEvent event) {
        //show transfer control to the main window
        //validate user entries

        FXMLLoader GUI_InterfaceLoader = new FXMLLoader();
        GUI_InterfaceLoader.setLocation(getClass().getResource("/myStage.fxml"));
        Parent root = null;
        try {
            root = GUI_InterfaceLoader.load(getClass().getResource("/myStage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
        // Hide this current window (if this is what you want)
        rootAnchorPane.getScene().getWindow().hide();

    }

}
