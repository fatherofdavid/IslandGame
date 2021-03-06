package com.annapolisWorks;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
    private Label alertUserLabel;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Button startButton;

    ObservableList<String> adventurers = FXCollections.observableArrayList("not selected", "Explorer", "Pilot",
            "Engineer", "Messenger", "Navigator", "Diver");

    ArrayList<ChoiceBox> choiceBoxes;

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

        choiceBoxes = new ArrayList<ChoiceBox>();
        choiceBoxes.add(player1ChoiceBox);
        choiceBoxes.add(player2ChoiceBox);
        choiceBoxes.add(player3ChoiceBox);
        choiceBoxes.add(player4ChoiceBox);

        //hefty code can be added to force no duplicate choices and at least 2 players
    }

    @FXML
    void startUp(ActionEvent event) {
        //pull in player list
        ArrayList<String> roster = new ArrayList();
        String newPlayer = "";
        for (ChoiceBox cb : choiceBoxes) {
            if(cb.getValue() != adventurers.get(0)) {
                newPlayer = (String)cb.getValue();
                if(roster.contains(newPlayer)) {
                    alertUserLabel.setText("Can't choose 2 identical team members");
                    return;
                }
                else roster.add(newPlayer);
            }
        }
        if(roster.size() < 2) {
            alertUserLabel.setText("Please choose at least 2 players");
            return;
        }
        //get water level
        int waterLevel;
        if(difficultyNoviceRadio.isSelected()) waterLevel = 2;
        else if(difficultyNoviceRadio.isSelected()) waterLevel = 3;
        else waterLevel = 4;

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/myStage.fxml"));
        Group root = new Group();
        Parent parent = null;
        try {
            parent = fxmlLoader.load(getClass().getResource("/myStage.fxml"));
            parent = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //start the game, load GUI into model and vice versa
        GUI_Controller GUI = (GUI_Controller)fxmlLoader.getController();
        GameEngine newGame = new GameEngine(GUI, waterLevel, roster);
        ((GUI_Controller)fxmlLoader.getController()).setModel(newGame);
        root.getChildren().add(parent);

        //show new window, hide current one
        Stage stage = new Stage();
        stage.setTitle("Forbidden Island");
        stage.setScene(new Scene(root, 1030, 595));
        stage.show();

        rootAnchorPane.getScene().getWindow().hide();
    }

}

/*
////////EXPERIMENTAL CODE SNIPPET CREATING INTRO ANIMATION
        Canvas canvas = new Canvas( 900, 512 ); //dev
        root.getChildren().add( canvas ); //dev
        //animation here
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(new Image("/helicopter.png"),200,200);
        root.getChildren().remove( canvas );
 */