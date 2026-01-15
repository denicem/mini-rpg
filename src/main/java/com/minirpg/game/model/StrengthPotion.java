package com.minirpg.game.model;

public class StrengthPotion implements Item {

    @Override
    public String getName() {
        return "Strength Potion";
    }

    @Override
    public void use(Player player) {
        player.getStats().setAtk(player.getStats().getAtk() + 5);
    }
}
