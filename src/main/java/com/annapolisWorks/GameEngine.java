package com.annapolisWorks;

import java.util.ArrayList;
import java.util.Random;

public class GameEngine {
    private ArrayList<Adventurer> roster;
    private Adventurer currentPlayer;
    private ArrayList<Treasure> capturedTreasures;
    private int waterLevel;
    GUI_Controller GUI;
    private Tile[][] gameBoard = new Tile[4][4];
    private Tile helipad;

    public GameEngine(GUI_Controller newGUI, int startingWaterLevel, ArrayList<String> rosterStrings){
        waterLevel = startingWaterLevel;
        GUI = newGUI;
        capturedTreasures = new ArrayList<>();

        //build the roster of Adventurer objects from the list of strings sent from intro GUI
        roster = new ArrayList<>();
        //for now all adventurers start on the helipad, which is always at 2, 2. Can upgrade later.
        helipad = new Tile(2, 2);
        helipad.setTreasureAccess(Treasure.HELI);
        for (String advName : rosterStrings) {
            roster.add(createAdventurer(advName, helipad));
        }
        currentPlayer = roster.get(0);
        currentPlayer.remainingActions = 4f;
        GUI.loadPlayers(roster);
        GUI.setWaterLevel(waterLevel);

        //build the game board
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                gameBoard[x][y] = new Tile(x, y);
            }
        }
        //can randomize this for more complex gameplay
        //remember these will return false if an overwrite is attempted
        //also, need to write this to the GUI somehow
        gameBoard[1][0].setTreasureAccess(Treasure.FIRE);
        GUI.setTileAccess(Treasure.FIRE.name(), 1, 0);
        gameBoard[2][0].setTreasureAccess(Treasure.FIRE);
        GUI.setTileAccess(Treasure.FIRE.name(), 2, 0);
        gameBoard[3][1].setTreasureAccess(Treasure.WATER);
        GUI.setTileAccess(Treasure.WATER.name(), 3, 1);
        gameBoard[3][2].setTreasureAccess(Treasure.WATER);
        GUI.setTileAccess(Treasure.WATER.name(), 3, 2);
        gameBoard[1][3].setTreasureAccess(Treasure.AIR);
        GUI.setTileAccess(Treasure.AIR.name(), 1, 3);
        gameBoard[2][3].setTreasureAccess(Treasure.AIR);
        GUI.setTileAccess(Treasure.AIR.name(), 2, 3);
        gameBoard[0][1].setTreasureAccess(Treasure.EARTH);
        GUI.setTileAccess(Treasure.EARTH.name(), 0, 1);
        gameBoard[0][2].setTreasureAccess(Treasure.EARTH);
        GUI.setTileAccess(Treasure.EARTH.name(), 0, 2);
    }

    public void treasureCaptured(Treasure newTreasure) {
        capturedTreasures.add(newTreasure);
        GUI.newTreasureCaptured(newTreasure);
    }

    public boolean alreadyCaptured(Treasure newTreasure) {
        for (Treasure treas : capturedTreasures) {
            if(treas.name() == newTreasure.name()) return true;
        }
        return false;
    }

    public Adventurer createAdventurer(String adventurerName, Tile startingTile) {
        switch(adventurerName) {
            case "Explorer" : {return new Explorer(startingTile, this); }
            case "Pilot" : return new Pilot(startingTile, this);
            case "Engineer" : return new Engineer(startingTile, this);
            case "Messenger" : return new Messenger(startingTile, this);
            case "Navigator" : return new Navigator(startingTile, this);
            case "Diver" : return new Diver(startingTile, this);
            default: throw new RuntimeException("attempted to create an unsupported adventurer type");
        }
    }

    private void floodRandom(int waterLevel) {
        int randX, randY;
        Random rand = new Random();
        Tile floodingTile;
        for(int i = 0; i < waterLevel; i++) {
            randX = rand.nextInt(4);
            randY = rand.nextInt(4);
            floodingTile = gameBoard[randX][randY];
            floodingTile.flood();
            if(floodingTile.getSubmersion() == 1) GUI.floodTile(randX, randY);
            else if(floodingTile.getSubmersion() == 2) GUI.sinkTile(randX, randY);
        }
    }

    private void waterRise() {
        waterLevel++;
        GUI.setWaterLevel(waterLevel);
        //notify user that waters have risen
    }

    public void endTurn() {
        drawCards(2);
        floodRandom(waterLevel);
        if(roster.indexOf(currentPlayer) == roster.size() - 1) {
            currentPlayer = roster.get(0);
        }
        else {
            currentPlayer = roster.get(roster.indexOf(currentPlayer) + 1);
        }
        currentPlayer.remainingActions = 4;
        GUI.nextTurn(currentPlayer, 4);
        if(currentPlayer instanceof Pilot) ((Pilot) currentPlayer).resetFly();
    }

    void requestMove(int x, int y) {
        int lastx, lasty;
        lastx = currentPlayer.myTile.getX();
        lasty = currentPlayer.myTile.getY();
        if(x == lastx && y == lasty) GUI.tellUser("You're on that tile.");
        else if(currentPlayer.move(gameBoard[x][y])) {
            GUI.movePlayerIcon(currentPlayer.getName(), lastx, lasty, x, y);
        }
        else GUI.tellUser("That tile is out of range.");
    }

    void requestShoreUp(int x, int y) {
        if(currentPlayer.shoreUp(gameBoard[x][y])) {
            gameBoard[x][y].shoreUp();
            GUI.shoreTile(x, y);
        }
        else {
            GUI.tellUser("Can't shore up that tile.");
        }
    }

    void requestCaptureTreasure() {
        Treasure newCapture = currentPlayer.captureTreasure();
        if(newCapture == null) GUI.tellUser("Can't capture any treasure");
    }

    void requestFly(int x, int y) {
        int lastx = currentPlayer.myTile.getX();
        int lasty = currentPlayer.myTile.getY();
        if(((Pilot) currentPlayer).fly(gameBoard[x][y])) {
            GUI.flyUsed();
            GUI.movePlayerIcon(currentPlayer.getName(), lastx, lasty, currentPlayer.myTile.getX(),
                    currentPlayer.myTile.getY());
        }
        else {
            GUI.tellUser("Can't fly there.");
        }
    }

    void requestGuide(String guidedPlayerName, int x, int y) {
        Adventurer guidedPlayer = null;
        for(Adventurer adv : roster) {
            if(adv.getName() == guidedPlayerName) guidedPlayer = adv;
        }
        if(guidedPlayer == null) throw new RuntimeException("Attempted to guide a non-real player");
        int lastx = guidedPlayer.myTile.getX();
        int lasty = guidedPlayer.myTile.getY();
        if(((Navigator) currentPlayer).guide(guidedPlayer, gameBoard[x][y])) {
            GUI.movePlayerIcon(guidedPlayer.getName(), lastx, lasty, guidedPlayer.myTile.getX(),
                    guidedPlayer.myTile.getY());
        }
        else {
            GUI.tellUser("Can't move that user there");
        }
        GUI.resetSelectedPlayer();
    }

    void requestSwim(int x, int y) {
        int lastx = currentPlayer.myTile.getX();
        int lasty = currentPlayer.myTile.getY();
        if(((Diver) currentPlayer).swim(gameBoard[x][y])) {
            //this function needs to be finished
            GUI.movePlayerIcon(currentPlayer.getName(), lastx, lasty, currentPlayer.myTile.getX(),
                    currentPlayer.myTile.getY());
        }
        else {
            GUI.tellUser("Can't swim there.");
        }
    }

    void requestHelicopter(int x, int y) {
        int lastx = currentPlayer.myTile.getX();
        int lasty = currentPlayer.myTile.getY();
        if(currentPlayer.Helicopter(gameBoard[x][y])) {
            discardCard(currentPlayer, ActionCard.HELICOPTER);
            GUI.movePlayerIcon(currentPlayer.getName(), lastx, lasty, currentPlayer.myTile.getX(),
                    currentPlayer.myTile.getY());
            GUI.actionCardWasUsed();
        }
        else GUI.tellUser("Can't helicopter there");
    }

    void requestSandbag(int x, int y) {
        if(currentPlayer.sandBag(gameBoard[x][y])) {
            discardCard(currentPlayer, ActionCard.SANDBAG);
            GUI.shoreTile(x, y);
            GUI.actionCardWasUsed();
        }
        else GUI.tellUser("Can't sandbag that tile.");
    }

    private void drawCards(int n) {
        //as if 3 helicopter, 3 water rise, 3 sandbag, and 5x4 treasure cards
        //helicopter chance: 3/29, water rise: 3/29, sandbag 3/29, each treasure 5/29
        int k;
        Random rand = new Random();
        for(int i = 0; i < n; i++) {
            k = rand.nextInt(29);
            if (k <= 3) waterRise();
            else if (k > 3 && k <= 6) currentPlayer.addCard(ActionCard.HELICOPTER);
            else if (k > 6 && k <= 9) currentPlayer.addCard(ActionCard.SANDBAG);
            else if (k > 9 && k <= 14) currentPlayer.addCard(Treasure.FIRE);
            else if (k > 14 && k <= 19) currentPlayer.addCard(Treasure.WATER);
            else if (k > 19 && k <= 24) currentPlayer.addCard(Treasure.AIR);
            else currentPlayer.addCard(Treasure.EARTH);
            //Need to tell User what cards were drawn - preferably with some kind of pop-up
        }
    }

    private void discardCard(Adventurer adv, ActionCard card) {
        for(ActionCard usedCard : adv.myActionCards) {
            if(usedCard.name() == card.name()) {
                currentPlayer.myActionCards.remove(usedCard);
                break;
            }
        }
    }

    public void checkForVictory() {
        //check for victory
        if(capturedTreasures.size() == 4) {
            boolean allArePresent = true;
            for(Adventurer adv : roster) {
                if(adv.myTile != helipad) allArePresent = false;
            }
            if(allArePresent) {
                System.out.println("you have won");
                System.exit(0);
            }
        }

    }

}

class Tile {
    private int xCoord;
    private int yCoord;
    private int submersion;
    private Treasure treasureAccess;

    Tile(int x, int y) {
        xCoord = x;
        yCoord = y;
        submersion = 0;
    }

    public int getX() {return xCoord;}
    public int getY() {return yCoord;}
    public int getSubmersion() {return submersion;}
    public Treasure getTreasureAccess() {return treasureAccess;}

    //returns false if an overwrite was attempted
    public boolean setTreasureAccess(Treasure treasure) {
        if(treasureAccess == null) {
            treasureAccess = treasure;
            return true;
        }
        else return false;
    }

    //once it is submersed 2 or more, it has sunk and cannot be shored up
    public boolean shoreUp() {
        if(submersion == 1) {
            submersion--;
            return true;
        }
        else return false;
    }

    public void flood() {
        submersion++;
    }
}

enum Treasure implements Card{ FIRE, WATER, AIR, EARTH, HELI;
}
