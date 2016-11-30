package com.annapolisWorks;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class GUI_Controller implements Initializable {
    //set to 1 for move, 2 for shore, 3 for give card, 5 for fly, 6 for swim, 7 for guide
    private int actionSelected = 0;
    private GameEngine model;
    private ArrayList<Label> playerLabels;
    private ArrayList<Button> primaryActionButtons;
    private ImageView selectedPlayer;
    private boolean playerHasBeenSelected;
    private Label usedCardLabel;

    @FXML
    private Label waterLevelLabel;

    @FXML
    private GridPane capturedTreasuresGridPane;


    @FXML
    private Label currentPlayerLabel;

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
    private Label tellUserLabel;

    @FXML
    private VBox actionCardsVBox;

    @FXML
    private TilePane treasureCardsTilePane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerLabels = new ArrayList<Label>();
        playerLabels.add(player1Label);
        playerLabels.add(player2Label);
        playerLabels.add(player3Label);
        playerLabels.add(player4Label);

        primaryActionButtons = new ArrayList<Button>();
        primaryActionButtons.add(moveButton);
        primaryActionButtons.add(shoreUpButton);
        primaryActionButtons.add(giveTreasureCardButton);
        primaryActionButtons.add(captureTreasureButton);
        primaryActionButtons.add(flyButton);
        primaryActionButtons.add(swimButton);
        primaryActionButtons.add(guideButton);

        playerHasBeenSelected = false;
    }

    public void setModel(GameEngine newGame) {
        model = newGame;
    }

    public void loadPlayers(ArrayList<Adventurer> roster) {
        Adventurer adv;
        for (int i = 0; i < roster.size(); i++) {
            adv = roster.get(i);
            addNewPlayer(adv.getName(), adv.myTile.getX(), adv.myTile.getY());
            playerLabels.get(i).setText(adv.getName());
        }
        currentPlayerLabel = player1Label;
        currentPlayerLabel.setText(">" + currentPlayerLabel.getText());
        hideIrrelevantActions(roster.get(0));
        showActionCards(roster.get(0));
    }

    public void addNewPlayer(String name, int tileX, int tileY) {
        ImageView character = null;
        switch(name) {
            case "Explorer" : {
                character = new ImageView("/explorer.png");
                character.setId("Explorer");
                break;
            }
            case "Pilot" : {
                character = new ImageView("/pilot.png");
                character.setId("Pilot");
                break;
            }
            case "Engineer" : {
                character = new ImageView("/engineer.png");
                character.setId("Engineer");
                break;
            }
            case "Messenger" : {
                character = new ImageView("/messenger.png");
                character.setId("Messenger");
                break;
            }
            case "Navigator" : {
                character = new ImageView("/navigator.png");
                character.setId("Navigator");
                break;
            }
            case "Diver" : {
                character = new ImageView("/diver.png");
                character.setId("Diver");
                break;
            }

        }
        VBox newVBox = getXY_VBox(tileX, tileY);
        newVBox.getChildren().add(character);
        character.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedPlayer = (ImageView) event.getTarget();
            }
        });
    }

    public void setActionsRemaining(float remainingActions) {
        remainingActionsLabel.setText("" + remainingActions);
        if(remainingActions == 0) disablePrimaryActions();
    }

    public void disablePrimaryActions() {
        for(Button btn : primaryActionButtons) {
            btn.setDisable(true);
        }
    }
    public void enablePrimaryActions() {
        for(Button btn : primaryActionButtons) {
            btn.setDisable(false);
        }
    }

    public void flyUsed() { flyButton.setDisable(true); }
    public void resetFly() { flyButton.setDisable(false); }

    private void showActionCards(Adventurer currentPlayer) {
        actionCardsVBox.getChildren().removeAll();
        for(ActionCard actionCard : currentPlayer.myActionCards) {
            Label newAction = new Label(actionCard.name());
            newAction.setVisible(true);
            if("HELICOPTER".equals(actionCard.name())) {
                newAction.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        actionSelected = 8;
                        usedCardLabel = (Label) event.getSource();
                        //to win, a helicopter must be used
                        model.checkForVictory();
                    }
                });
            }
            else if("SANDBAG".equals(actionCard.name())) {
                newAction.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        actionSelected = 9;
                        usedCardLabel = (Label) event.getSource();
                    }
                });
            }
            else throw new RuntimeException("There is an abnormal action card in the player's hand.");
            actionCardsVBox.getChildren().add(newAction);
        }
    }

    public void actionCardWasUsed() {
        if(usedCardLabel != null) {
            usedCardLabel.setVisible(false);
            usedCardLabel.setManaged(false);
        }
        else throw new RuntimeException("Tried to delete a nonexistent action card.");
    }

    private void showTreasureCards(Adventurer currentPlayer) {
        treasureCardsTilePane.getChildren().removeAll();
        for(Treasure treasure : currentPlayer.myTreasureCards) {
            ImageView newTreasureCard = null;
            switch(treasure.name()) {
                case "FIRE" : {
                    newTreasureCard = new ImageView("/fire.png");
                    break;
                }
                case "WATER" : {
                    newTreasureCard = new ImageView("/water.png");
                    break;
                }
                case "AIR" : {
                    newTreasureCard = new ImageView("/wind.png");
                    break;
                }
                case "EARTH" : {
                    newTreasureCard = new ImageView("/earth.png");
                    break;
                }
            }
            if(newTreasureCard != null) treasureCardsTilePane.getChildren().add(newTreasureCard);
            else throw new RuntimeException("attempted to add null treasure card");
        }
    }


    public void nextTurn(Adventurer activePlayer, int remainingActions) {
        remainingActionsLabel.setText(""+remainingActions);
        resetFly();

        //update '>' to point at current player
        String id = activePlayer.getName();
        currentPlayerLabel.setText(currentPlayerLabel.getText().substring(1));
        for (Label label : playerLabels) {
            if(id.equals(label.getText())) {
                label.setText(">" + label.getText());
                currentPlayerLabel = label;
            }
        }

        enablePrimaryActions();
        hideIrrelevantActions(activePlayer);
        showActionCards(activePlayer);
        showTreasureCards(activePlayer);
    }

    private void hideIrrelevantActions(Adventurer activePlayer) {
        //toggle active appropriate Action buttons
        Method[] methodList = activePlayer.getClass().getDeclaredMethods();
        if(checkHasMethod("fly", methodList)) {
            flyButton.setVisible(true);
            flyButton.setManaged(true);
        }
        else {
            flyButton.setVisible(false);
            flyButton.setManaged(false);
        }
        if(checkHasMethod("guide", methodList)) {
            guideButton.setVisible(true);
            guideButton.setManaged(true);
        }
        else {
            guideButton.setVisible(false);
            guideButton.setManaged(false);
        }
        if(checkHasMethod("swim", methodList)) {
            swimButton.setVisible(true);
            swimButton.setManaged(true);
        }
        else {
            swimButton.setVisible(false);
            swimButton.setManaged(false);
        }
    }

    private boolean checkHasMethod(String desiredMethod, Method[] methodlist) {
        for(Method m : methodlist) {
            if(desiredMethod.equals(m.getName())) return true;
        }
        return false;
    }

    public void setWaterLevel(int waterLevel) {
        waterLevelLabel.setText("" + waterLevel);
    }

    public void setTileAccess(String element, int tileX, int tileY) {
        ImageView access = null;
        switch(element) {
            case "FIRE": {
                access = new ImageView("/fire.png");
                break;
            }
            case "WATER": {
                access = new ImageView("/water.png");
                break;
            }
            case "AIR": {
                access = new ImageView("/wind.png");
                break;
            }
            case "EARTH": {
                access = new ImageView("/earth.png");
                break;
            }
        }
        VBox newVBox = getXY_VBox(tileX, tileY);
        newVBox.getChildren().add(access);
    }

    public void floodTile(int x, int y) {
        ImageView tileToFlood = getXYImage(x, y);
        tileToFlood.setOpacity(0.6);
    }

    public void sinkTile(int x, int y) {
        ImageView tileToFlood = getXYImage(x, y);
        tileToFlood.setOpacity(0d);
    }

    public void shoreTile(int x, int y) {
        ImageView tileToShore = getXYImage(x, y);
        tileToShore.setOpacity(1d);
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

    public void newTreasureCaptured(Treasure treasure) {
        ImageView newTreasureImage;
        switch(treasure) {
            case FIRE : {
                newTreasureImage = new ImageView("/fire.png");
                capturedTreasuresGridPane.add(newTreasureImage, 0, 0);
                break;
            }
            case WATER : {
                newTreasureImage = new ImageView("/water.png");
                capturedTreasuresGridPane.add(newTreasureImage, 1, 0);
                break;
            }
            case AIR : {
                newTreasureImage = new ImageView("/wind.png");
                capturedTreasuresGridPane.add(newTreasureImage, 0, 1);
                break;
            }
            case EARTH : {
                newTreasureImage = new ImageView("/earth.png");
                capturedTreasuresGridPane.add(newTreasureImage, 1, 1);
                break;
            }
        }
    }


    //Section to access particular tiles in order to move user images or show a tile as flooded
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

    //creating an x-y lookup for the game board anchor panes, thereby the image & grid panes
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

    public void tellUser(String str) {
        tellUserLabel.setText(str);
    }


    public void resetActionSwitch() {
        actionSelected = 0;
        playerHasBeenSelected = false;
    }
    public void resetSelectedPlayer() {
        selectedPlayer = null;
    }

    //user button commands
    public void tileClicked(Event event) {
        int x = GridPane.getColumnIndex((Node)(event.getSource()));
        int y = GridPane.getRowIndex((Node)(event.getSource()));
        switch(actionSelected) {
            case 0: break;
            case 1: {
                model.requestMove(x, y);
                resetActionSwitch();
                break;
            }
            case 2: {
                model.requestShoreUp(x, y);
                resetActionSwitch();
                break;
            }
            case 3: {
                //add code to give cards
                resetActionSwitch();
                break;
            }
            case 4: {
                model.requestCaptureTreasure();
                resetActionSwitch();
                break;
            }
            case 5: {
                model.requestFly(x, y);
                resetActionSwitch();
                break;
            }
            case 6: {
                model.requestSwim(x, y);
                resetActionSwitch();
                break;
            }
            case 7: {
                //flag to ignore first tile click when guiding
                System.out.println("" + playerHasBeenSelected);
                if(playerHasBeenSelected) {
                    model.requestGuide(selectedPlayer.getId(), x, y);
                    playerHasBeenSelected = false;
                    resetActionSwitch();
                }
                else playerHasBeenSelected = true;
                break;
            }
            case 8: {
                model.requestHelicopter(x, y);
                resetActionSwitch();
                break;
            }
            case 9: {
                model.requestSandbag(x, y);
                resetActionSwitch();
                break;
            }

        }
    }

    public void moveClicked(ActionEvent event){
        actionSelected = 1;
    }
    public void shoreUpClicked(ActionEvent event){
        actionSelected = 2;
    }
    public void giveCardClicked(ActionEvent event){ actionSelected = 3; }
    public void captureTreasureClicked(ActionEvent event){
        model.requestCaptureTreasure();
    }
    public void flyClicked(ActionEvent event){
        actionSelected = 5;
    }
    public void swimClicked(ActionEvent event){
        actionSelected = 6;
    }
    public void guideClicked(ActionEvent event){
        actionSelected = 7;
    }
    public void useCardClicked(ActionEvent event){
        //delete this button
    }
    public void endTurnClicked(ActionEvent event){
        model.endTurn();
    }
}

/*
Useful code snippet for next time:

 */
