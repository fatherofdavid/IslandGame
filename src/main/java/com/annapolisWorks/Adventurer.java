package com.annapolisWorks;

import java.util.ArrayList;

public abstract class Adventurer {
    public int remainingActions;
    protected String myName;
    protected Tile myTile;
    protected ArrayList<ActionCard> myActionCards;
    protected ArrayList<TreasureCard> myTreasureCards;

    public Adventurer(Tile startingTile) {
        myTile = startingTile;
    }

    //this should not be used
    protected Adventurer() {}

    public String getName() {
        return myName;
    }

    boolean isAccessible(Tile newTile){
        if((newTile.getX() - myTile.getX() == 0) && (Math.abs(newTile.getY() - myTile.getY())) == 1)
            return true;
        else if((newTile.getY() - myTile.getY() == 0) && (Math.abs(newTile.getX() - myTile.getX())) == 1)
            return true;
        else
            return false;
    }

    public void move(Tile newTile) {
        if(isAccessible(newTile)) {
            myTile = newTile;
            actionUsed();
        }
    }

    public void shoreUp(Tile adjTile) {
        if(isAccessible(adjTile)) {
            myTile = adjTile;
            actionUsed();
        }
    }

    public void giveCard(Adventurer teammate, ActionCard card) {
        if(teammate.myTile == myTile) {
        }
    }

    public void actionUsed() {
        //this does not need to necessarily be overwritten, but need to have better UI interface platform
        throw new RuntimeException("action used was not overwritten");
    }

    public void addCard(Card card) {
        if(card instanceof ActionCard) myActionCards.add((ActionCard)card);
        else if(card instanceof TreasureCard) myTreasureCards.add((TreasureCard)card);
        else throw new RuntimeException("Problem with cards and type-casting");
    }
}

class Explorer extends Adventurer {

    public Explorer(Tile myStartingTile) {
        super(myStartingTile);
        myName = "Explorer";
    }

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

    public Pilot(Tile myStartingTile) {
        super(myStartingTile);
        myName = "Pilot";
    }

    public void fly(Tile newTile) {
        if(newTile.getSubmersion() > 1) myTile = newTile;
    }
}