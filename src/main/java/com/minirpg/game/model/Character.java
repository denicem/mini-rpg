package com.minirpg.game.model;

public abstract class Character {
    protected String name;
    //protected Stats stats;

    public Character(String name) {
        this.name = name;
    //  this.stats = stats;
    }

    public String getName() {
        return name;
    }

    //public Stats getStats() {
    //    return stats;
    //}

    public boolean isAlive() {
        return true;
    }

    public abstract String attack(Character target);

    protected int hp = 100;
    protected int maxHp = 100;

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setHp(int hp) {
        this.hp = Math.min(hp, maxHp);
    }

}
