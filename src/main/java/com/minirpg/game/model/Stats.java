package com.minirpg.game.model;

/**
 * Represents the stats of a character in the MiniRPG game.
 * Each character has attack (atk) and defense (def) values that influence combat.
 */

public class Stats {
    private int atk;
    private int def;

    public Stats(int atk, int def) {
        this.atk = atk;
        this.def = def;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }
}
