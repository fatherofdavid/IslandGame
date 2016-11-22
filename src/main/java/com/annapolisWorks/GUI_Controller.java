package com.annapolisWorks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GUI_Controller {
    Label currentPlayerLabel;

    @FXML
    private Label player1Label;

    @FXML
    private Label player2Label;

    @FXML
    private Label player3Label;

    @FXML
    private Label player4Label;


    @FXML
    Label remainingActionsLabel;

    @FXML
    private Button moveButton;

    @FXML
    private Button shoreUpButton;

    @FXML
    private Button giveTreasureCardButton;

    @FXML
    private Button captureTreasureButton;

    @FXML
    private Button flyButton;

    @FXML
    private Button swimButton;

    @FXML
    private Button guideButton;

    @FXML
    private Button useCardButton;

    public void startUp() {
        currentPlayerLabel = player1Label;
    }

    public void actionUsed(float used) {
        float remainingActions = Float.parseFloat(remainingActionsLabel.getText());
        remainingActions = remainingActions - used;
        remainingActionsLabel.setText(""+remainingActions);
    }

    public void flyUsed() {
        flyButton.setDisable(true);
    }

    public void nextTurn(Adventurer activePlayer) {
        String id = activePlayer.getName();
        currentPlayerLabel.setText(currentPlayerLabel.getText().substring(1));
        if(id == player1Label.getText()) player1Label.setText(">" + player1Label.getText());
        else if(id == player2Label.getText()) player1Label.setText(">" + player1Label.getText());
        else if(id == player3Label.getText()) player1Label.setText(">" + player1Label.getText());
        else if(id == player4Label.getText()) player1Label.setText(">" + player1Label.getText());
        else throw new RuntimeException("attempting to switch turn to non-existent player");

        //draw right-panel buttons  and windows correctly for new player
    }

    //user button commands
    public void move(ActionEvent event){
    }
    public void shoreUp(ActionEvent event){
    }
    public void giveCard(ActionEvent event){
    }
    public void captureTreasure(ActionEvent event){
    }
    public void fly(ActionEvent event){
        flyUsed();
    }
    public void swim(ActionEvent event){
    }
    public void guide(ActionEvent event){
    }
    public void useCard(ActionEvent event){
    }
    public void endTurn(ActionEvent event){
    }
}

/*
        Useful code for later: (makes a button disappear)
        System.out.println("I've been clicked!");
        ((Button)event.getSource()).setVisible(false);
        moveButton.setManaged(false);
 */