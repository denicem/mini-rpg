package com.minirpg.game.model;

public final class GameSession {
    private static Player player;
    private static Enemy currentEnemy;

    private static Item pendingItem;

    private GameSession() {}

    public static void startNewGame() {
        player = new Player("Knight");
        currentEnemy = null;
        pendingItem = null;
    }

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player p) {
        player = p;
    }

    public static Enemy getCurrentEnemy() {
        return currentEnemy;
    }

    public static void setCurrentEnemy(Enemy enemy) {
        currentEnemy = enemy;
    }

    public static void clearCurrentEnemy() {
        currentEnemy = null;
    }

    public static void giveItem(Item item) {
        pendingItem = item;
    }

    public static Item peekPendingItem() {
        return pendingItem;
    }

    public static Item consumePendingItem() {
        Item temp = pendingItem;
        pendingItem = null;
        return temp;
    }

    public static void clearPendingItem() {
        pendingItem = null;
    }
}

