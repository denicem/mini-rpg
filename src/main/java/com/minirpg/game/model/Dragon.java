package com.minirpg.game.model;

public class Dragon extends Enemy{
    public Dragon(){
        super("Dragon", 50, new Stats(5,5));
    }

    @Override
    public String attack(Character target) {
        return BattleSystem.performAttack(this, target);
    }
}
