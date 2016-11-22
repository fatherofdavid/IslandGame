package com.annapolisWorks;

import java.util.ArrayList;

public class Game {
    ArrayList<Adventurer> roster;
    int waterLevel;
    Tile[][] gameBoard = new Tile[4][4];

    void newGame(){

        //build the game board
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                gameBoard[x][y] = new Tile(x, y);
            }
        }

        Pilot player1 = new Pilot(gameBoard[0][0]);
        player1.fly(gameBoard[3][3]);

    }


}

class Tile {
    private int xCoord;
    private int yCoord;
    private int submersion;

    Tile(int x, int y) {
        xCoord = x;
        yCoord = y;
        submersion = 0;
    }

    int getX() {return xCoord;}
    int getY() {return yCoord;}
    int getSubmersion() {return submersion;}

    //once it is submersed 2 or more, it has sunk and cannot be shored up
    void shoreUp() {
        if (submersion == 1) submersion--;
    }
}