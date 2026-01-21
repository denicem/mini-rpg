package com.minirpg.game.model.character;


import com.minirpg.game.model.Stats;

/**
 * Abstract class representing an enemy in the MiniRPG game.
 * Enemies are characters controlled by the game and typically oppose the player.
 */
public abstract class Enemy extends Character {
    public Enemy(String name, int hp, Stats stats){
        super(name, hp, stats);
    }
}
