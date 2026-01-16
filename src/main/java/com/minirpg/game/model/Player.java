package com.minirpg.game.model;

import java.util.ArrayList;
import java.util.List;

public class Player extends Character {
    protected List<Item> items;

    public Player(String name) {
        super(name, 100, new Stats(10, 5));
        this.items = new ArrayList<>();
    }

    @Override
    public String attack(Character target) {
        return BattleSystem.performAttack(this, target);
    }
}
