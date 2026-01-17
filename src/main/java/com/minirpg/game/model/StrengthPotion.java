package com.minirpg.game.model;

/**
 * Represents a Strength Potion item in the MiniRPG game.
 * When used, it increases the player's attack by 5 points.
 */

public class StrengthPotion implements Item {

    //Returns the name of the item.

    @Override
    public String getName() {
        return "Strength Potion";
    }

    /**
     * Uses the item on the player.
     * Increases the player's attack (atk) by 5.
     */

    @Override
    public void use(Player player) {
        player.getStats().setAtk(player.getStats().getAtk() + 5);
    }
}
