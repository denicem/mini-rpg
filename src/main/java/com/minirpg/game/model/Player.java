package com.minirpg.game.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player character in the MiniRPG game.
 * The player is controlled by the user and can hold items.
 */

public class Player extends Character {
    protected List<Item> items;

    public Player(String name) {
        super(name, 100, new Stats(10, 5));
        this.items = new ArrayList<>();
    }

    //Attacks a target character using the battle system.

    @Override
    public String attack(Character target) {
        return BattleSystem.performAttack(this, target);
    }
}
