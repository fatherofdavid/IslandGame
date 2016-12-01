package com.annapolisWorks;

import java.util.ArrayList;

public abstract class Adventurer {
    public float remainingActions;
    protected String myName;
    protected Tile myTile;
    protected ArrayList<ActionCard> myActionCards;
    protected ArrayList<Treasure> myTreasureCards;
    protected GameEngine myGameEngine;

    public Adventurer(Tile startingTile, GameEngine gameEngine) {
        myTile = startingTile;
        myGameEngine = gameEngine;
        myActionCards = new ArrayList<>();
        myTreasureCards = new ArrayList<>();
    }

    //this should not be used - IDE is forcing its creation
    protected Adventurer() {}

    public String getName() {
        return myName;
    }
    public void setTile(Tile newTile) {myTile = newTile;};

    boolean isAccessible(Tile newTile){
        if(newTile.getSubmersion() > 1) return false;
        else if(isAdjacent(newTile)) return true;
        else return false;
    }

    boolean isAdjacent(Tile newTile) {
        if(newTile == myTile) {
            return true;
        }
        else if((newTile.getX() - myTile.getX() == 0) && (Math.abs(newTile.getY() - myTile.getY())) == 1)
            return true;
        else if((newTile.getY() - myTile.getY() == 0) && (Math.abs(newTile.getX() - myTile.getX())) == 1)
            return true;
        else return false;
    }

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
            actionUsed(1f);
            return true;
        }
        else return false;
    }

    public Treasure captureTreasure() {
        if(myTile.getTreasureAccess() != null && remainingActions >= 1 && myTile.getSubmersion() == 0) {
            int treasureCardCount = 0;
            for(Treasure t : myTreasureCards) {
                if(t.name() == myTile.getTreasureAccess().name()) treasureCardCount++;
            }
            if(!myGameEngine.alreadyCaptured(myTile.getTreasureAccess()) & treasureCardCount >= 4) {
                actionUsed(1);
                int costOfTreasure = 4;
                for(int i = 0, deleted = 0; i < myTreasureCards.size() && deleted < (costOfTreasure + 1); i++ ) {
                    if(myTreasureCards.get(i).name() == myTile.getTreasureAccess().name()) {
                        myTreasureCards.remove(i);
                        i = 0;
                        deleted++;
                    }
                }
                return myTile.getTreasureAccess();
            }
        }
        return null;
    }

    public boolean Helicopter(Tile newTile) {
        if(newTile.getSubmersion() < 2) {
            myTile = newTile;
            return true;
        }
        else return false;
    }

    public boolean sandBag(Tile floodedTile) {
        if(floodedTile.getSubmersion() == 1) {
            floodedTile.shoreUp();
            return true;
        }
        else return false;
    }

    public void actionUsed(float usedActionCount) {
        remainingActions = remainingActions - usedActionCount;
        myGameEngine.GUI.setActionsRemaining(remainingActions);
    }

    public void addCard(Card card) {
        if(card instanceof ActionCard) myActionCards.add((ActionCard)card);
        else if(card instanceof Treasure) myTreasureCards.add((Treasure)card);
        //else do nothing - in case water_rises card is drawn
    }

    public void removeCard(Card card) {
        if(card instanceof ActionCard) myActionCards.remove((ActionCard)card);
        else if(card instanceof Treasure) myTreasureCards.remove((Treasure)card);
        else throw new RuntimeException("Problem with cards and type-casting");
    }
}

class Explorer extends Adventurer {
    public Explorer(Tile myStartingTile, GameEngine myGameEngine) {
        super(myStartingTile, myGameEngine);
        myName = "Explorer";
    }

    @Override
    boolean isAdjacent(Tile newTile) {
        if(myTile == newTile) return true;
        else if((Math.abs(newTile.getX() - myTile.getX()) <= 1) &&
                (Math.abs(newTile.getY() - myTile.getY())) <= 1)
            return true;
        else return false;
    }
}

class Pilot extends Adventurer {
    boolean flightUsed = false;

    public Pilot(Tile myStartingTile, GameEngine myGameEngine) {
        super(myStartingTile, myGameEngine);
        myName = "Pilot";
    }

    public boolean fly(Tile newTile) {
        if(newTile.getSubmersion() < 2 && remainingActions >= 1 && !flightUsed) {
            flightUsed = true;
            actionUsed(1);
            myTile = newTile;
            return true;
        }
        else return false;
    }

    public void resetFly() {
        flightUsed = false;
    }
}

class Engineer extends Adventurer {
    public Engineer(Tile myStartingTile, GameEngine myGameEngine) {
        super(myStartingTile, myGameEngine);
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
    public Messenger(Tile myStartingTile, GameEngine myGameEngine) {
        super(myStartingTile, myGameEngine);
        myName = "Messenger";
    }

    @Override
    public boolean giveCard(Adventurer teammate, Card card) {
        if(remainingActions >= 1) {
            teammate.addCard(card);
            this.removeCard(card);
            actionUsed(1f);
            return true;
        }
        else return false;
    }
}

class Navigator extends Adventurer {
    public Navigator(Tile myStartingTile, GameEngine myGameEngine) {
        super(myStartingTile, myGameEngine);
        myName = "Navigator";
    }

    public boolean guide(Adventurer adv, Tile newTile) {
        if(adv.isAccessible(newTile)) {
            adv.setTile(newTile);
            actionUsed(0.5f);
            return true;
        }
        else return false;
    }
}

class Diver extends Adventurer {
    public Diver(Tile myStartingTile, GameEngine myGameEngine) {
        super(myStartingTile, myGameEngine);
        myName = "Diver";
    }

    //This doesn't fully work. It doesn't have any way to tell when you're done, or to stop you
    //from stopping on a sunk tile
    public boolean swim(Tile newTile) {
        if(isAdjacent(newTile) && remainingActions >= 1) {
            myTile = newTile;
            if(newTile.getSubmersion() == 0) {
                actionUsed(1);
                return true;
            }
            return true;
        }
        else return false;
    }

}