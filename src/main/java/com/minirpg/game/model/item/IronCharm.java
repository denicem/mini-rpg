package com.minirpg.game.model.item;

import com.minirpg.game.model.character.Player;

/**
 * Represents an Iron Charm item in the MiniRPG game.
 * When used, it increases the player's defense.
 */

public class IronCharm implements Item {
//Returns the name of the item.
    @Override
    public String getName() {
        return "Iron Charm";
    }


    /**
     * Uses the item on the player.
     * Increases the player's defense by 3 points
     */

    @Override
    public void use(Player player) {
        player.getStats().setDef(player.getStats().getDef() + 3);
    }
}

