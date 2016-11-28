package com.annapolisWorks;

import java.util.ArrayList;

public abstract class Adventurer {
<<<<<<< HEAD
    public int remainingActions;
=======
    public float remainingActions;
>>>>>>> df82c19705a31d396212dae2a6c411fe3898aed6
    protected String myName;
    protected Tile myTile;
    protected ArrayList<ActionCard> myActionCards;
    protected ArrayList<TreasureCard> myTreasureCards;

    public Adventurer(Tile startingTile) {
        myTile = startingTile;
    }

<<<<<<< HEAD
    //this should not be used
=======
    //this should not be used - IDE is forcing its creation
>>>>>>> df82c19705a31d396212dae2a6c411fe3898aed6
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

<<<<<<< HEAD
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
=======
    public boolean move(Tile newTile) {
        if(isAccessible(newTile) && remainingActions >= 1) {
            myTile = newTile;
            actionUsed(1);
            return true;
        }
        else return false;
    }

    public boolean shoreUp(Tile adjTile) {
        if(isAccessible(adjTile) && remainingActions >= 1) {
            if(adjTile.shoreUp()) {
                actionUsed(1);
                return true;
            }
        }
        return false;
    }

    public boolean giveCard(Adventurer teammate, Card card) {
        if(teammate.myTile == myTile && remainingActions >= 1) {
            teammate.addCard(card);
            this.removeCard(card);
            return true;
        }
        else return false;
    }

    public boolean captureTreasure(Game game, Treasure treasure) {
        if(myTile.getTreasureAccess() == treasure && remainingActions >= 1) {
            if(!game.alreadyCaptured(treasure)) {
                game.treasureCaptured(treasure);
                actionUsed(1);
                return true;
            }
        }
        return false;
    }

    public void actionUsed(float usedActionCount) {
        remainingActions = remainingActions - usedActionCount;
>>>>>>> df82c19705a31d396212dae2a6c411fe3898aed6
    }

    public void addCard(Card card) {
        if(card instanceof ActionCard) myActionCards.add((ActionCard)card);
        else if(card instanceof TreasureCard) myTreasureCards.add((TreasureCard)card);
        else throw new RuntimeException("Problem with cards and type-casting");
    }
<<<<<<< HEAD
}

class Explorer extends Adventurer {

=======

    public void removeCard(Card card) {
        if(card instanceof ActionCard) myActionCards.remove((ActionCard)card);
        else if(card instanceof TreasureCard) myTreasureCards.remove((TreasureCard)card);
        else throw new RuntimeException("Problem with cards and type-casting");
    }
}

class Explorer extends Adventurer {
>>>>>>> df82c19705a31d396212dae2a6c411fe3898aed6
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
<<<<<<< HEAD
=======
    boolean flightUsed = false;
>>>>>>> df82c19705a31d396212dae2a6c411fe3898aed6

    public Pilot(Tile myStartingTile) {
        super(myStartingTile);
        myName = "Pilot";
    }

<<<<<<< HEAD
    public void fly(Tile newTile) {
        if(newTile.getSubmersion() > 1) myTile = newTile;
    }
=======
    public boolean fly(Tile newTile) {
        if(newTile.getSubmersion() < 2 && remainingActions >= 1 && !flightUsed) {
            flightUsed = true;
            actionUsed(1);
            myTile = newTile;
            return true;
        }
        else return false;
    }
}

class Engineer extends Adventurer {
    public Engineer(Tile myStartingTile) {
        super(myStartingTile);
        myName = "Engineer";
    }

    @Override
    public boolean shoreUp(Tile adjTile) {
        if(isAccessible(adjTile)) {
            if(adjTile.shoreUp()) {
                actionUsed(0.5f);
                return true;
            }
        }
        return false;
    }
}

class Messenger extends Adventurer {
    public Messenger(Tile myStartingTile) {
        super(myStartingTile);
        myName = "Messenger";
    }

    @Override
    public boolean giveCard(Adventurer teammate, Card card) {
        if(remainingActions >= 1) {
            teammate.addCard(card);
            this.removeCard(card);
            return true;
        }
        else return false;
    }
}

class Diver extends Adventurer {
    public Diver(Tile myStartingTile) {
        super(myStartingTile);
        myName = "Diver";
    }

    //!! this doesn't fully work. It doesn't have any way to tell when you're done, or to stop you
    //from stopping on a sunk tile
    //need to fix Git
    public boolean swim(Tile newTile) {
        if(remainingActions >= 1) {
            myTile = newTile;
            if(newTile.getSubmersion() < 1) {
                actionUsed(1);
                return true;
            }
            return true;
        }
        else return false;
    }

>>>>>>> df82c19705a31d396212dae2a6c411fe3898aed6
}