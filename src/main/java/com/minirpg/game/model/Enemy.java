package com.minirpg.game.model;

public class Enemy extends Character{
    public Enemy(String name, Stats stats){
        super(name);
    }

    @Override
    public String attack(Character target) {
        return "";
    }
}
