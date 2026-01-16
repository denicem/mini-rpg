package com.minirpg.game.model;

import com.minirpg.game.util.Assets;

public class TestLogic {
    public static void main(String[] args) {
        System.out.println("Hellooo");
        Player p = new Player("Hero");

        System.out.println(p.getName() + " is alive: " + p.isAlive());
        System.out.println(p.attack(p));

        Assets assets = new Assets();
        System.out.println(Assets.BG_CASTLE_INFRONT);
    }
}
