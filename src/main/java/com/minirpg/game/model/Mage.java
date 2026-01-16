package com.minirpg.game.model;

public class Mage extends Enemy{
    public Mage(){
        super("Mage", 20, new Stats(3,1));
    }

    @Override
    public String attack(Character target) {
        return BattleSystem.performAttack(this, target);
    }
}
