package com.minirpg.game.model;
/**
 * Represents a Potion item in the MiniRPG game.
 * When used, it restores 20 HP to the player, without exceeding the maximum HP.
 */

public class Potion implements Item{
    //Returns the name of the item.
    @Override
    public String getName() {
        return "Potion";
    }


    /**
     * Uses the item on the player.
     * Restores 20 HP, but not above the player's maximum HP.
     */

    @Override
    public void use(Player p) {
        p.setHp(Math.min(p.getHp() + 20, p.getMaxHp()));

    }
}
