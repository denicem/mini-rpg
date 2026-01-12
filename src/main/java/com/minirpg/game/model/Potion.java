package com.minirpg.game.model;

public class Potion implements Item{
    @Override
    public String getName() {
        return "Potion";
    }

    @Override
    public void use(Player p) {
        p.setHp(Math.min(p.getHp() + 20, p.getMaxHp()));

    }
}
