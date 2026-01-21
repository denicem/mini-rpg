package com.minirpg.game.model.character;

import com.minirpg.game.model.Stats;
import com.minirpg.game.util.BattleSystem;

/**
 * Represents a Dragon enemy in the MiniRPG game.
 * The Dragon is a specific type of Enemy with predefined stats and health.
 */

public class  Dragon extends Enemy{
    public Dragon(){
        super("Dragon", 50, new Stats(5,5));
    }


    // Attacks a target character using the battle system.


    @Override
    public String attack(Character target) {
        return BattleSystem.performAttack(this, target);
    }
}
