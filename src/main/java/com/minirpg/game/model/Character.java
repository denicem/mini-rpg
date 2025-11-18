package com.minirpg.game.model;

public abstract class Character {
    protected String name;
    //protected Stats stats;

    public Character(String name) {
        this.name = name;
    //  this.stats = stats;
    }

    public String getName() {
        return name;
    }

    //public Stats getStats() {
    //    return stats;
    //}

    public boolean isAlive() {
        return true;
    }

    public abstract String attack(Character target);
}
