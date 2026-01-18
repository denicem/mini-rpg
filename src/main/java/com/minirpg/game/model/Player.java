package com.minirpg.game.model;

import com.minirpg.game.util.BattleSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player character in the MiniRPG game.
 * The player is controlled by the user and can hold items.
 */

public class Player extends Character {
    protected List<Item> inventory;

    public Player(String name) {
        super(name, 100, new Stats(10, 5));
        this.inventory = new ArrayList<>();
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public String addItem(Item item) {
        this.inventory.add(item);
        return item.getName() + " added to inventory.";
    }

    public String useItem(Item item) {
        if (this.inventory.contains(item)) {
            item.use(this);
            this.inventory.remove(item);

            return this.getName() + " used " + item.getName();
        }
        return "Item does not exist.";
    }

    //Attacks a target character using the battle system.

    @Override
    public String attack(Character target) {
        return BattleSystem.performAttack(this, target);
    }
}
