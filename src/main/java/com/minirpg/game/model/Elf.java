package com.minirpg.game.model;

public class Elf extends Enemy{
    public Elf(){
        super("Elf", 20, new Stats(2,1));
    }

    @Override
    public String attack(Character target) {
        return BattleSystem.performAttack(this, target);
    }
}
