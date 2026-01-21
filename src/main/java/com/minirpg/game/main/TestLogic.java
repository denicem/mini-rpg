package com.minirpg.game.main;

import com.minirpg.game.model.character.Player;
import com.minirpg.game.util.Assets;

public class TestLogic {
    public static void main(String[] args) {
        System.out.println("Hellooo");
        Player p = new Player("Hero");

        System.out.println(p.getName() + " is alive: " + p.isAlive());
        System.out.println(p.attack(p));

        System.out.println(Assets.BG_CASTLE_INFRONT);
    }
}
