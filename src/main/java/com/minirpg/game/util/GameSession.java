package com.minirpg.game.util;

import com.minirpg.game.model.Enemy;
import com.minirpg.game.model.Item;
import com.minirpg.game.model.Player;

public final class GameSession {
    private static String selectedCharacterType; // Spielertyp wird hier gespeichert (Knight oder Knight_girl)
    private static String characterImgPath;
    private static Player player;
    private static Enemy currentEnemy;
    private static Item pendingItem;
    private static int currentAct = 0;
    private static StoryManager.Ending finalEnding;

    private GameSession() {}

    public static void startNewGame() {
        player = new Player("Knight");
        currentEnemy = null;
        pendingItem = null;
        currentAct = 1;
    }

    public static String getSelectedCharacterType() {
        return selectedCharacterType;
    }
    public static void setSelectedCharacterType(String selectedCharacterType) {
        GameSession.selectedCharacterType = selectedCharacterType;
        GameSession.setCharacterImgPath();
    }

    public static String getCharacterImgPath() {
        return characterImgPath;
    }
    private static void setCharacterImgPath() {
        GameSession.characterImgPath = "/com/minirpg/game/characters/" + GameSession.selectedCharacterType + ".png";
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

    public static int getCurrentAct() {
        return currentAct;
    }
    public static void setCurrentAct(int currentAct) {
        GameSession.currentAct = currentAct;
    }
    public static void nextAct() {
        ++currentAct;
    }

    public static StoryManager.Ending getFinalEnding() {
        return finalEnding;
    }
    public static void setFinalEnding(StoryManager.Ending ending) {
        finalEnding = ending;
    }
}
