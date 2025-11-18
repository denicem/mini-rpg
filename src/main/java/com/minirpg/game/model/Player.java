package com.minirpg.game.model;

import java.util.ArrayList;
import java.util.List;

public class Player extends Character {
    //private List<Item> inventory = new ArrayList<>();

    public Player(String name) {
        super(name);
    }

    @Override
    public String attack(Character target) {
        return getName() + " attacks " + target.getName() + "!";
    }

    //public void addItem(Item item) {
    //    inventory.add(item);
    //}

    //public List<Item> getInventory() {
    //    return inventory;
    //}
}
