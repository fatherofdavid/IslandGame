package com.annapolisWorks;

import java.util.ArrayList;

public abstract class Adventurer {
    public int remainingActions;
    public String myName;
    protected Tile myTile;
    protected ArrayList<ActionCard> myActionCards;
    protected ArrayList<TreasureCard> myTreasureCards;

    public Adventurer(Tile startingTile) {
        myTile = startingTile;
    }

    //this should not be used
    protected Adventurer() {}

    boolean isAccessible(Tile newTile){
        if((newTile.getX() - myTile.getX() == 0) && (Math.abs(newTile.getY() - myTile.getY())) == 1)
            return true;
        else if((newTile.getY() - myTile.getY() == 0) && (Math.abs(newTile.getX() - myTile.getX())) == 1)
            return true;
        else
            return false;
    }

    void move(Tile newTile) {
        if(isAccessible(newTile)) {
            myTile = newTile;
            actionUsed();
        }
    }

    void shoreUp(Tile adjTile) {
        if(isAccessible(adjTile)) {
            myTile = adjTile;
            actionUsed();
        }
    }

    void giveCard(Adventurer teammate, ActionCard card) {
        if(teammate.myTile == myTile) {
        }
    }

    void actionUsed() {
        //this does not need to necessarily be overwritten, but need to have better UI interface platform
        throw new RuntimeException("action used was not overwritten");
    }

    void addCard(Card card) {
        if(card instanceof ActionCard) myActionCards.add((ActionCard)card);
        else if(card instanceof TreasureCard) myTreasureCards.add((TreasureCard)card);
        else throw new RuntimeException("Problem with cards and type-casting");
    }
}

class Explorer extends Adventurer {
    @Override
    boolean isAccessible(Tile newTile) {
        if(newTile.getSubmersion() > 1)
            return false;
        if(myTile == newTile)
            return false;
        else if((Math.abs(newTile.getX() - myTile.getX()) <= 1) &&
                (Math.abs(newTile.getY() - myTile.getY())) <= 1)
            return true;
        else
            return false;
    }
}

class Pilot extends Adventurer {

    Pilot(Tile startingTile) {super(startingTile);}

    void fly(Tile newTile) {
        if(newTile.getSubmersion() > 1) myTile = newTile;
    }
}