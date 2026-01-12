package com.minirpg.game.model;

public class Player extends Character {

    private Stats stats;

    public Player(String name) {
        super(name);
        this.stats = new Stats(10, 5); // Startwerte: ATK, DEF
    }

    public Stats getStats() {
        return stats;
    }

    @Override
    public String attack(Character target) {
        return getName() + " attacks " + target.getName() + "!";
    }
}
