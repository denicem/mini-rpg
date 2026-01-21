package com.minirpg.game.model.character;


import com.minirpg.game.model.Stats;
import com.minirpg.game.util.BattleSystem;

/**
 * Represents a Mage enemy in the MiniRPG game.
 * The Mage is an enemy with balanced offensive and defensive stats.
 */

public class Mage extends Enemy{
    public Mage(){
        super("Mage", 20, new Stats(3,1));
    }
  //Attacks a target character using the battle system.
    @Override
    public String attack(Character target) {
        return BattleSystem.performAttack(this, target);
    }
}
