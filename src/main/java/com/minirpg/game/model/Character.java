package com.minirpg.game.model;

import java.util.List;

/**
 * Abstract base class representing a character in the MiniRPG game.
 * A character has a name, health points, maximum health points, and stats.
 * This class defines common behavior shared by all character types.
 */

public abstract class Character {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected Stats stats;

    public Character(String name, int hp, Stats stats) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public Stats getStats() {
        return stats;
    }

    public void setHp(int hp) {
        this.hp = Math.min(hp, maxHp);
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    /**
     * Performs an attack on another character.
     * Must be implemented by subclasses.
     */

    public abstract String attack(Character target);

}
