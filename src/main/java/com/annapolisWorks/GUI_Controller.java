package com.annapolisWorks;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI_Controller implements Initializable {
    //set to 1 for move, 2 for shore, 3 for give card, 5 for fly, 6 for swim, 7 for guide
    private int actionSelected = 0;
    private GameEngine model;
    private ArrayList<Label> playerLabels;
    private ArrayList<Button> allActionButtons;
    private ArrayList<Button> strandedActionButtons;
    private ImageView selectedPlayer;
    private boolean selectingPlayer;
    private ImageView usedCardImageView;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label waterLevelLabel;

    @FXML
    private TilePane capturedTreasuresTilePane;

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
    private TilePane notifyUserTilePane;

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
    private Button endTurnButton;

    @FXML
    private Button nextButton;

    @FXML
    private Label tellUserLabel;

    @FXML
    private TilePane actionCardsTilePane;

    @FXML
    private TilePane treasureCardsTilePane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerLabels = new ArrayList<Label>();
        playerLabels.add(player1Label);
        playerLabels.add(player2Label);
        playerLabels.add(player3Label);
        playerLabels.add(player4Label);

        allActionButtons = new ArrayList<Button>();
        allActionButtons.add(moveButton);
        allActionButtons.add(shoreUpButton);
        allActionButtons.add(giveTreasureCardButton);
        allActionButtons.add(captureTreasureButton);
        allActionButtons.add(flyButton);
        allActionButtons.add(swimButton);
        allActionButtons.add(guideButton);
        allActionButtons.add(endTurnButton);

        strandedActionButtons = new ArrayList<Button>();
        strandedActionButtons.add(moveButton);
        strandedActionButtons.add(flyButton);
        strandedActionButtons.add(swimButton);

        selectingPlayer = true;
        selectedPlayer = new ImageView();
        nextButton.setVisible(false);
        nextButton.setManaged(false);
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
        if(character == null) throw new RuntimeException("Tried to create nonexistent adventurer");
        VBox newVBox = getXY_VBox(tileX, tileY);
        newVBox.getChildren().add(character);
        character.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedPlayer.setImage(((ImageView) event.getTarget()).getImage());
                selectedPlayer.setId(((ImageView) event.getTarget()).getId());

                //will only add user to selected User box if user is doing Guide or Give Card
                if(notifyUserTilePane.getChildren().size() == 0 && (actionSelected == 7 || actionSelected == 3)) {
                    tellUser("Selected Player:");
                    notifyUserTilePane.getChildren().add(selectedPlayer);
                }
            }
        });
    }

    public void setActionsRemaining(float remainingActions) {
        remainingActionsLabel.setText("" + remainingActions);
        if(remainingActions == 0) disablePrimaryActions();
    }

    public void disablePrimaryActions() {
        for(Button btn : allActionButtons) {
            btn.setDisable(true);
        }
        endTurnButton.setDisable(false);
    }
    public void enableAllActions() {
        for(Button btn : allActionButtons) {
            btn.setDisable(false);
        }
    }

    public void flyUsed() { flyButton.setDisable(true); }
    public void resetFly() { flyButton.setDisable(false); }

    private void showActionCards(Adventurer currentPlayer) {
        actionCardsTilePane.getChildren().clear();
        for(ActionCard actionCard : currentPlayer.myActionCards) {
            ImageView newAction = generateCardImage(actionCard);
            newAction.setVisible(true);
            if("HELICOPTER".equals(actionCard.name())) {
                newAction.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        actionSelected = 8;
                        usedCardImageView = (ImageView) event.getSource();
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
                        usedCardImageView = (ImageView) event.getSource();
                    }
                });
            }
            else throw new RuntimeException("There is an abnormal action card in the player's hand.");
            actionCardsTilePane.getChildren().add(newAction);
        }
    }

    public void actionCardWasUsed() {
        if(usedCardImageView != null) {
            usedCardImageView.setVisible(false);
            usedCardImageView.setManaged(false);
        }
        else throw new RuntimeException("Tried to delete a nonexistent action card.");
    }

    public void showDrawnCard(ArrayList<Card> drawnCards) {
        //This needs to be improved with a window that fits the buttons.
        //the INFORMATION Alert is not letting my scale the size
        //deploying as-is for now.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("New Cards Drawn!");
        alert.setHeaderText(null);
        HBox tempHBox = new HBox();
        tempHBox.setSpacing(10);
        for(Card card : drawnCards) {
            tempHBox.getChildren().add(generateCardImage(card));
        }
        alert.setGraphic(tempHBox);
        alert.showAndWait();
    }

    public void showTreasureCards(Adventurer currentPlayer) {
        treasureCardsTilePane.getChildren().clear();
        for(Treasure treasure : currentPlayer.myTreasureCards) {
            ImageView newTreasureCard = generateCardImage(treasure);
            newTreasureCard.setId(treasure.name());
            newTreasureCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(selectedPlayer.getImage() != null) {
                        model.requestGive(selectedPlayer.getId(), ((ImageView)event.getSource()).getId());
                    }
                    resetActionSwitch();
                }
            });
            if(newTreasureCard != null) treasureCardsTilePane.getChildren().add(newTreasureCard);
            else throw new RuntimeException("attempted to add null treasure card");
        }
    }


    public void nextTurn(Adventurer activePlayer, int remainingActions) {
        remainingActionsLabel.setText(""+remainingActions);
        //update '>' to point at current player
        String id = activePlayer.getName();
        currentPlayerLabel.setText(currentPlayerLabel.getText().substring(1));
        for (Label label : playerLabels) {
            if(id.equals(label.getText())) {
                label.setText(">" + label.getText());
                currentPlayerLabel = label;
            }
        }
        enableAllActions();
        hideIrrelevantActions(activePlayer);
        showActionCards(activePlayer);
        showTreasureCards(activePlayer);
        notifyUserTilePane.getChildren().clear();
        tellUserLabel.setText("");
    }

    public void strandedTurn(Adventurer strandedPlayer) {
        enableAllActions();
        hideIrrelevantActions(strandedPlayer);
        for(Button btn : allActionButtons) {
            if(!strandedActionButtons.contains(btn)) btn.setDisable(true);
            else btn.setDisable(false);
        }
        nextButton.setVisible(true);
        nextButton.setManaged(true);
        actionCardsTilePane.getChildren().clear();
        treasureCardsTilePane.getChildren().clear();
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
        nextButton.setVisible(false);
        nextButton.setManaged(false);
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

    public void setTileAccess(Treasure element, int tileX, int tileY) {
        ImageView newTreasureAcessImage = generateCardImage(element);
        VBox newVBox = getXY_VBox(tileX, tileY);
        newVBox.getChildren().add(newTreasureAcessImage);
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
        ImageView newTreasureImage = generateCardImage(treasure);
        capturedTreasuresTilePane.getChildren().add(newTreasureImage);
    }

    private ImageView generateCardImage(Card card) {
        ImageView newCardImage = null;
        switch(card.name()) {
            case "FIRE" : {
                newCardImage = new ImageView("/fire.png");
                break;
            }
            case "WATER" : {
                newCardImage = new ImageView("/water.png");
                break;
            }
            case "AIR" : {
                newCardImage = new ImageView("/wind.png");
                break;
            }
            case "EARTH" : {
                newCardImage = new ImageView("/earth.png");
                break;
            }
            case "HELICOPTER" : {
                newCardImage = new ImageView("/helicopter.png");
                break;
            }
            case "SANDBAG" : {
                newCardImage = new ImageView("/sandbags.png");
                break;
            }
            case "WATER_RISES" : {
                newCardImage = new ImageView("/water rises.png");
                break;
            }
        }
        return newCardImage;
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
        selectingPlayer = true;
        resetSelectedPlayer();
    }
    public void resetSelectedPlayer() {
        notifyUserTilePane.getChildren().clear();
        selectedPlayer.setImage(null);
        selectedPlayer.setId("");
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
                //this will finish executing upon click of the treasure Card
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
                //flag to ignore first tile click, because it is choosing a player not a tile
                if(!selectingPlayer && selectedPlayer.getImage() != null) {
                    model.requestGuide(selectedPlayer.getId(), x, y);
                    resetActionSwitch();
                }
                else selectingPlayer = false;
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
        tellUser("Click where you want to move");
    }
    public void shoreUpClicked(ActionEvent event){
        actionSelected = 2;
        tellUser("Click the tile you want to shore up");
    }
    public void giveCardClicked(ActionEvent event){ actionSelected = 3;
        tellUser("First click player, then click Treasure Card");}
    public void captureTreasureClicked(ActionEvent event){
        model.requestCaptureTreasure();
    }
    public void flyClicked(ActionEvent event){
        actionSelected = 5;
        tellUser("Click the tile you want to fly to");
    }
    public void swimClicked(ActionEvent event){
        actionSelected = 6;
        tellUser("Click the tile you want to swim to. As long as you stay in water, you can keep swimming without using an action");
    }
    public void guideClicked(ActionEvent event){
        actionSelected = 7;
        tellUser("First click player, then click Tile you want to guide him to");
    }
    public void endTurnClicked(ActionEvent event){
        model.endTurn();
    }
    public void nextClicked(ActionEvent event){ model.strandedPlayersMove();}

    public void fileExit() {
        System.exit(0);
    }

    public void fileNewGame() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/intro.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load(getClass().getResource("/intro.fxml"));
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Forbidden Island");
        stage.setScene(new Scene(root, 544, 360));
        stage.show();
        rootAnchorPane.getScene().getWindow().hide();
    }

    public void helpAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setContentText("This is an attempt to reproduce the board game Forbidden Island by Gamewright.\n" +
                "It was developed for learning purposes and not intended to be used commercially.\n" +
                "Developed by Jesse Taylor, November 2016.");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void helpInstructions(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instructions");
        //Need to get this section working still
        /*
        StringBuilder sb = new StringBuilder();
        try {
            Scanner s = new Scanner(new File("/instructions.txt")).useDelimiter("\\s+");
            while (s.hasNext()) {
                sb.append(s.next() + " ");
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        alert.setContentText(sb.toString());
        */
        alert.setContentText("You need 4 treasure cards to capture a treasure.\n" +
                "\n" +
                "Once your team has captured all 4 treasures, go to the helicopter pad and use a helicopter to win.\n");
        alert.setHeaderText(null);
        alert.showAndWait();

    }
}

/*
Useful code snippet for next time:

 */
