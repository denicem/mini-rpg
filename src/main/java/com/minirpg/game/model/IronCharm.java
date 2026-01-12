package com.minirpg.game.model;

public class IronCharm implements Item {

    @Override
    public String getName() {
        return "Iron Charm";
    }

    @Override
    public void use(Player player) {
        player.getStats().setDef(player.getStats().getDef() + 3);
    }
}

