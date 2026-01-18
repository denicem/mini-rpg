package com.minirpg.game.model;

import com.minirpg.game.util.BattleSystem;

/**
 * Represents an Elf enemy in the MiniRPG game.
 * The Elf is a weaker enemy with lower health and stats.
 */

public class Elf extends Enemy{
    public Elf(){
        super("Elf", 20, new Stats(2,1));
    }

    // Attacks a target character using the battle system.


    @Override
    public String attack(Character target) {
        return BattleSystem.performAttack(this, target);
    }
}
