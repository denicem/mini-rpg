package com.minirpg.game.model;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String attack(Character target) {
        return BattleSystem.performAttack(this, target);
    }
}
