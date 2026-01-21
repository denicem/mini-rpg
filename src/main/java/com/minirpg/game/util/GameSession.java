package com.minirpg.game.util;

import com.minirpg.game.model.*;

import java.util.ArrayList;
import java.util.List;

public final class GameSession {
    private static String selectedCharacterType; // Spielertyp wird hier gespeichert (Knight oder Knight_girl)
    private static String characterImgPath;
    private static Player player;
    private static Enemy currentEnemy;
    private static Item pendingItem;
    private static int currentAct = 0;
    private static StoryManager.Ending finalEnding;
    private static int enemyCount;

    private GameSession() {}

    public static void startNewGame(String name) {
        player = new Player(name);
        currentEnemy = null;
        pendingItem = null;
        enemyCount = 0;
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

    public static void createCurrentEnemy(boolean isBoss) {
        if (isBoss)
            currentEnemy = new Dragon();
        else if (enemyCount == 0)
            currentEnemy = new Elf();
        else if (enemyCount == 1)
            currentEnemy = new Mage();
        else
            currentEnemy = (Math.random() < 0.5 ? new Elf() : new Mage());
        ++enemyCount;
    }

    public static List<Item> createAndGetReward() {
        List<Item> reward = new ArrayList<>();

        // Standard Reward (Exactly one item)
        double standardRoll = Math.random();
        if (standardRoll < 0.33) {
            reward.add(new Potion());
        } else if (standardRoll < 0.67) {
            reward.add(new StrengthPotion());
        } else {
            reward.add(new IronCharm());
        }

        // Extra reward if lucky!
        double roll = Math.random();
        if (roll > 0.60)
            reward.add(new Potion());
        if (roll > 0.75)
            reward.add(new StrengthPotion());
        if (roll > 0.67)
            reward.add(new IronCharm());

        player.getInventory().addAll(reward);
        return reward;
    }
}
