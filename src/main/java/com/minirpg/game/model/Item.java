package com.minirpg.game.model;

/**
 * Represents an item that can be used by the player in the MiniRPG game.
 * Items provide effects when used, such as increasing stats.
 */

public interface Item {
    String getName();
    void use(Player player);
}

