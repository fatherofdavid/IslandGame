package com.annapolisWorks;

import java.util.ArrayList;
import java.util.Random;

public class GameEngine {
    ArrayList<Adventurer> roster;
    Adventurer currentPlayer;
    private ArrayList<Treasure> capturedTreasures;
    int waterLevel;
    GUI_Controller GUI;
    Tile[][] gameBoard = new Tile[4][4];

    public GameEngine(GUI_Controller newGUI, int startingWaterLevel, ArrayList<String> rosterStrings){
        waterLevel = startingWaterLevel;
        GUI = newGUI;

        roster = new ArrayList<Adventurer>();
        Tile helipad = new Tile(2,2);
        helipad.setTreasureAccess(Treasure.HELI);
        for (String advName : rosterStrings) {
            roster.add(createAdventurer(advName, helipad));
        }
        //need to create GUI initialization functions, put user icons into helipad,
        //then need to build GUI side of actions
        currentPlayer = roster.get(0);

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
        gameBoard[2][0].setTreasureAccess(Treasure.FIRE);
        gameBoard[3][1].setTreasureAccess(Treasure.WATER);
        gameBoard[3][2].setTreasureAccess(Treasure.WATER);
        gameBoard[1][3].setTreasureAccess(Treasure.AIR);
        gameBoard[2][3].setTreasureAccess(Treasure.AIR);
        gameBoard[0][1].setTreasureAccess(Treasure.EARTH);
        gameBoard[0][2].setTreasureAccess(Treasure.EARTH);
    }

    /*
    I need to be able to call static functions to notify the GUI!
    or else GUI_Controller needs to be an object of EVERYTHING?
    which one is the acceptable design pattern?
    Can I use static functions in GUI controller?
    I don't think so... can't invoke non-static from static context... or something
    can't make them static because even that static couldn't invoke the very real object that we're modifying (the GUI)

    So... the Big model needs to do the reporting.
    if(activeUser.fly()) {GUI_Controller.flyUsed()}
    else {GUI_Controller.postToUser("Can't fly there.")}
    This is the only good design pattern.
     */


    public void treasureCaptured(Treasure newTreasure) {
        capturedTreasures.add(newTreasure);
    }
    public boolean alreadyCaptured(Treasure newTreasure) {
        for (Treasure treas : capturedTreasures) {
            if(treas.name() == newTreasure.name()) return true;
        }
        return false;
    }

    public Adventurer createAdventurer(String adventurerName, Tile startingTile) {
        switch(adventurerName) {
            case "Explorer" : {return new Explorer(startingTile); }
            case "Pilot" : return new Pilot(startingTile);
            case "Engineer" : return new Engineer(startingTile);
            case "Messenger" : return new Messenger(startingTile);
            case "Navigator" : return new Navigator(startingTile);
            case "Diver" : return new Diver(startingTile);
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

    public void endTurn() {
        floodRandom(waterLevel);
        if(roster.indexOf(currentPlayer) == roster.size() - 1) {
            currentPlayer = roster.get(0);
        }
        else {
            currentPlayer = roster.get(roster.indexOf(currentPlayer) + 1);
        }
        GUI.nextTurn(currentPlayer, 4);
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