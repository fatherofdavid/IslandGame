package com.annapolisWorks;

public interface Card {
    public String name();

}

enum Treasure implements Card{ FIRE, WATER, AIR, EARTH, HELICOPTER;
}

enum ActionCard implements Card{ HELICOPTER, SANDBAG
}

enum WaterRisesCard implements Card{ WATER_RISES
}
