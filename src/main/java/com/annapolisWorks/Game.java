package com.annapolisWorks;

import java.util.ArrayList;

public class Game {
    ArrayList<Adventurer> roster;
<<<<<<< HEAD
=======
    private ArrayList<Treasure> capturedTreasures;
>>>>>>> df82c19705a31d396212dae2a6c411fe3898aed6
    int waterLevel;
    Tile[][] gameBoard = new Tile[4][4];

    void newGame(GUI_Controller GUI){

        //build the game board
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                gameBoard[x][y] = new Tile(x, y);
            }
        }
<<<<<<< HEAD

        Pilot player1 = new Pilot(gameBoard[0][0]);
        player1.fly(gameBoard[3][3]);
        GUI.nextTurn(player1, 4);



    }


=======
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

        Pilot player1 = new Pilot(gameBoard[0][0]);
        player1.fly(gameBoard[3][3]);
        //GUI.nextTurn(player1, 4);
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
        for (Treasure t : capturedTreasures) {
            if(t.name() == newTreasure.name()) return true;
        }
        return false;
    }
>>>>>>> df82c19705a31d396212dae2a6c411fe3898aed6
}

class Tile {
    private int xCoord;
    private int yCoord;
    private int submersion;
<<<<<<< HEAD
=======
    private Treasure treasureAccess;
>>>>>>> df82c19705a31d396212dae2a6c411fe3898aed6

    Tile(int x, int y) {
        xCoord = x;
        yCoord = y;
        submersion = 0;
    }

<<<<<<< HEAD
    int getX() {return xCoord;}
    int getY() {return yCoord;}
    int getSubmersion() {return submersion;}

    //once it is submersed 2 or more, it has sunk and cannot be shored up
    void shoreUp() {
        if (submersion == 1) submersion--;
=======
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
>>>>>>> df82c19705a31d396212dae2a6c411fe3898aed6
    }
}