package com.annapolisWorks;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GUI_Controller implements Initializable {
    //set to 1 for move, 2 for shore, 3 for give card, 5 for fly, 6 for swim, 7 for guide
    int actionSelected = 0;

    Label currentPlayerLabel;

    @FXML
    private VBox jessesTest;

    @FXML
    private Label player1Label;

    @FXML
    private Label player2Label;

    @FXML
    private Label player3Label;

    @FXML
    private Label player4Label;

    @FXML
    private GridPane gameBoardGridPane;

    @FXML
    private AnchorPane tile0x0;

    @FXML
    private AnchorPane tile1x0;

    @FXML
    private AnchorPane tile2x0;

    @FXML
    private AnchorPane tile3x0;

    @FXML
    private AnchorPane tile0x1;

    @FXML
    private AnchorPane tile1x1;

    @FXML
    private AnchorPane tile2x1;

    @FXML
    private AnchorPane tile3x1;

    @FXML
    private AnchorPane tile0x2;

    @FXML
    private AnchorPane tile1x2;

    @FXML
    private AnchorPane tile2x2;

    @FXML
    private AnchorPane tile3x2;

    @FXML
    private AnchorPane tile0x3;

    @FXML
    private AnchorPane tile1x3;

    @FXML
    private AnchorPane tile2x3;

    @FXML
    private AnchorPane tile3x3;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentPlayerLabel = player1Label;
        Game mission = new Game();
        mission.newGame(this);
    }

    public void actionUsed(float used) {
        float remainingActions = Float.parseFloat(remainingActionsLabel.getText());
        remainingActions = remainingActions - used;
        remainingActionsLabel.setText(""+remainingActions);
    }

    public void flyUsed() { flyButton.setDisable(true); }
    public void resetFly() { flyButton.setDisable(false); }

    public void nextTurn(Adventurer activePlayer, int remainingActions) {
        remainingActionsLabel.setText(""+remainingActions);
        resetFly();

        String id = activePlayer.getName();
        currentPlayerLabel.setText(currentPlayerLabel.getText().substring(1));
        if(id.equals(player1Label.getText())) player1Label.setText(">" + player1Label.getText());
        else if(id.equals(player2Label.getText())) player1Label.setText(">" + player1Label.getText());
        else if(id.equals(player3Label.getText())) player1Label.setText(">" + player1Label.getText());
        else if(id.equals(player4Label.getText())) player1Label.setText(">" + player1Label.getText());
        else throw new RuntimeException("attempting to switch turn to non-existent player");

        //toggle active appropriate Action buttons
        Method[] methodlist = activePlayer.getClass().getDeclaredMethods();
        if(checkHasMethod("fly", methodlist)) {
            flyButton.setVisible(true);
            flyButton.setManaged(true);
        }
        else {
            flyButton.setVisible(false);
            flyButton.setManaged(false);
        }
        if(checkHasMethod("guide", methodlist)) {
            guideButton.setVisible(true);
            guideButton.setManaged(true);
        }
        else {
            guideButton.setVisible(false);
            guideButton.setManaged(false);
        }
        if(checkHasMethod("swim", methodlist)) {
            swimButton.setVisible(true);
            swimButton.setManaged(true);
        }
        else {
            swimButton.setVisible(false);
            swimButton.setManaged(false);
        }

        //Show Cards in the player's hand
    }

    private boolean checkHasMethod(String desiredMethod, Method[] methodlist) {
        for(Method m : methodlist) {
            if(desiredMethod.equals(m.getName())) return true;
        }
        return false;
    }

    public void movePlayerIcon(String characterId, int lastx, int lasty, int newx, int newy) {
        VBox lastVBox = getXY_VBox(lastx, lasty);
        ImageView myImage = null;
        for(Node n : lastVBox.getChildren()) {
            if(n instanceof ImageView) {
                if(n.getId() == characterId) {
                    myImage = (ImageView)n;
                }
            }
        }
        lastVBox.getChildren().remove(myImage);
        VBox newVBox = getXY_VBox(newx, newy);
        newVBox.getChildren().add(myImage);
    }


    /*
    **
    Section to access particular tiles in order to move user images or show a tile as flooded
    **
     */

    public ImageView getXYImage(int x, int y) {
        AnchorPane anchorPane = getXYPane(x, y);
        ObservableList<Node> anchorContents = anchorPane.getChildren();
        for (Node node : anchorContents) {
            if(node instanceof ImageView)
                return (ImageView)node;
        }
        throw new RuntimeException("the program tried to access a nonexistent node");
    }

    public VBox getXY_VBox(int x, int y) {
        AnchorPane anchorPane = getXYPane(x, y);
        ObservableList<Node> anchorContents = anchorPane.getChildren();
        for (Node node : anchorContents) {
            if(node instanceof VBox)
                return (VBox)node;
        }
        throw new RuntimeException("the program tried to access a nonexistent node");
    }

    //creating an x-y lookup for the game board anchor panes, thereby the image & gride panes
    public AnchorPane getXYPane(int x, int y) {
        switch (x) {
            case 0: {
                switch (y) {
                    case 0: return tile0x0;
                    case 1: return tile0x1;
                    case 2: return tile0x2;
                    case 3: return tile0x3;
                }
                break;
            }
            case 1: {
                switch (y) {
                    case 0: return tile1x0;
                    case 1: return tile1x1;
                    case 2: return tile1x2;
                    case 3: return tile1x3;
                }
                break;
            }
            case 2: {
                switch (y) {
                    case 0: return tile2x0;
                    case 1: return tile2x1;
                    case 2: return tile2x2;
                    case 3: return tile2x3;
                }
                break;
            }
            case 3: {
                switch (y) {
                    case 0: return tile3x0;
                    case 1: return tile3x1;
                    case 2: return tile3x2;
                    case 3: return tile3x3;
                }
                break;
            }
        }
        throw new RuntimeException("tried to access a non-existent tile");
    }


    public void move(int x, int y) {
        actionSelected = 0;
        // this was just for fun but it worked, if you put a playerIcon in the tile
        // movePlayerIcon("explorer", 2, 2, x, y);
    }


    //user button commands
    public void tileClicked(Event event) {

        int x = GridPane.getColumnIndex((Node)(event.getSource()));
        int y = GridPane.getRowIndex((Node)(event.getSource()));
        switch(actionSelected) {
            case 0: break;
            case 1: move(x, y);
            //need to add all the other actions
        }
    }

    public void moveClicked(ActionEvent event){
        actionSelected = 1;
    }
    public void shoreUp(ActionEvent event){
        actionSelected = 2;
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
Useful code snippet for next time:
        ImageView character = new ImageView("/explorer.png");
        character.setId("explorer");
 */
