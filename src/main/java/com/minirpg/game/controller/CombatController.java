package com.minirpg.game.controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import com.minirpg.game.model.*;
import com.minirpg.game.util.*;

public class CombatController {
    @FXML private ImageView backgroundView, playerImageView, enemyImageView;
    @FXML private Label playerHpLabel, playerAtkDefLabel, enemyHpLabel, enemyAtkDefLabel;
    @FXML private Label combatLog;
    @FXML private Button attackButton, doNothingButton, potionButton, fleeButton;

    private Player player;
    private Enemy enemy;

    private StoryManager sm = new StoryManager();
    private boolean isCombatOver = false;
    private boolean isItemMenuOpen = false;

    @FXML
    public void initialize() {
        Helper.logCall(this);

        // prepare Player and Enemy
        this.player = GameSession.getPlayer();
        GameSession.createCurrentEnemy(GameSession.getCurrentAct() == StoryManager.ACT_5);
        this.enemy = GameSession.getCurrentEnemy();

        // Bilder laden
        loadCombatScene();

        // binds one button property to another
        // -> call on. setVisible() from these buttons -> automatically triggers .setManaged() as well w the same value
        potionButton.managedProperty().bind(potionButton.visibleProperty());
        doNothingButton.managedProperty().bind(doNothingButton.visibleProperty());
        fleeButton.managedProperty().bind(fleeButton.visibleProperty());

        refreshUI();
        combatLog.setText(sm.getEnemyIntroText(StoryManager.EnemyType.DRAMATIC_ELF));
    }

    private void loadCombatScene() {
        Helper.loadImage(playerImageView, GameSession.getCharacterImgPath());

        if (enemy instanceof Dragon) {
            Helper.loadImage(backgroundView, Assets.BG_CASTLE_INSIDE);
            Helper.loadImage(enemyImageView, Assets.CH_DRAGON);
            return ;
        }

        Helper.loadImage(backgroundView, Assets.BG_DARK_FOREST);
        if (enemy instanceof Elf)
            Helper.loadImage(enemyImageView, Assets.CH_ELF);
        else if (enemy instanceof Mage)
            Helper.loadImage(enemyImageView, Assets.CH_MAGE);
        else
            System.err.println("Enemy Image not found.");
    }

    private void refreshUI() {
        updateStatsDisplay();

        if (isCombatOver) { // button layout after winning combat
            attackButton.setText("Go on");
            potionButton.setVisible(false);
            doNothingButton.setVisible(false);
            fleeButton.setVisible(false);
        }
        else if (isItemMenuOpen) { // button layout for selecting item
            attackButton.setText(String.format("HP Potion (%d)", countItems(Potion.class)));
            doNothingButton.setText(String.format("ATK Buff (%d)", countItems(StrengthPotion.class)));
            fleeButton.setText(String.format("DEF Buff (%d)", countItems(IronCharm.class)));
            potionButton.setText("BACK");

            attackButton.setDisable(countItems(Potion.class) == 0);
            doNothingButton.setDisable(countItems(StrengthPotion.class) == 0);
            fleeButton.setDisable(countItems(IronCharm.class) == 0);
        }
        else { // button layout for choosing combat action
            attackButton.setText("ATTACK!");
            doNothingButton.setText("DO NOTHING...");
            potionButton.setText(String.format("ITEMS (%d)", this.player.getInventory().size()));
            fleeButton.setText("FLEE");

            attackButton.setDisable(false);
            doNothingButton.setDisable(false);
            potionButton.setDisable(this.player.getInventory().isEmpty());
            fleeButton.setDisable(false);

            attackButton.setVisible(true);
            doNothingButton.setVisible(true);
            potionButton.setVisible(true);
            fleeButton.setVisible(true);
        }
    }

    private void updateStatsDisplay() { // updates HP, ATK & DEF stats
        playerHpLabel.setText(String.format("HP: %d/%d", player.getHp(), player.getMaxHp()));
        enemyHpLabel.setText(String.format("HP: %d/%d", enemy.getHp(), enemy.getMaxHp()));

        playerAtkDefLabel.setText(String.format("ATK: %d | DEF: %d",player.getStats().getAtk(), player.getStats().getDef()));
        enemyAtkDefLabel.setText(String.format("ATK: %d | DEF: %d",enemy.getStats().getAtk(), enemy.getStats().getDef()));
    }

    private String handleEnemyTurn() {
        return handleEnemyTurn(true);
    }

    private String handleEnemyTurn(boolean firstNewline) {
        String enemyLog = "";
        if (enemy.isAlive()) {
            if (firstNewline)
                enemyLog += '\n';
            enemyLog += BattleSystem.performAttack(enemy, player);
        }
        return enemyLog;
    }

    @FXML
    private void onAttackButtonClick() { // Attack/Potion Button
        if (isCombatOver) {
            ViewManager.switchTo("story-view.fxml");
            return ;
        }
        if (isItemMenuOpen) {
            useItemByType(Potion.class);
            return ;
        }
        StringBuilder turnLog = new StringBuilder();

        // Player's turn
        String playerAttackResult = BattleSystem.performAttack(player, enemy);
        turnLog.append(playerAttackResult).append('\n');
        combatLog.setText(turnLog.toString());
        System.out.println(playerAttackResult);

        // Enemy's turn
        turnLog.append(handleEnemyTurn());

        combatLog.setText(turnLog.toString());
        refreshUI();
        checkBattleStatus();
    }

    @FXML
    private void onDoNothingButtonClick() {
        if (isItemMenuOpen) {
            useItemByType(StrengthPotion.class);
            return ;
        }
        StringBuilder turnLog = new StringBuilder();

        // Enemy's turn
        turnLog.append(handleEnemyTurn(false));

        combatLog.setText(turnLog.toString());
        refreshUI();
        checkBattleStatus();
    }

    @FXML
    private void onPotionButtonClick() {
        isItemMenuOpen = !isItemMenuOpen;
        this.refreshUI();
    }

    @FXML
    private void onFleeButtonClick() {
        if (isItemMenuOpen) {
            useItemByType(IronCharm.class);
            return ;
        }
        this.combatLog.setText("You really wanna flee now? Bruh... NO!");
    }

    private void checkBattleStatus() {
        if (!player.isAlive()) {
            GameSession.setFinalEnding(StoryManager.Ending.BAD);
            ViewManager.switchTo("end-screen-view.fxml");
        } else if (!enemy.isAlive()) {
            handleVictory();
        }
    }

    private void handleVictory() {
        Helper.delay(1000, () -> {
            if (enemy instanceof Dragon) {
                GameSession.setFinalEnding(StoryManager.Ending.GOOD);
                ViewManager.switchTo("end-screen-view.fxml");
                return ;
            }
            List<Item> reward = GameSession.createAndGetReward();

            String lootMessage = (reward.size() < 2 ? "ONE ITEM" : " MORE ITEMS! YAY");
            combatLog.setText(lootMessage);
            isCombatOver = true;
            refreshUI();
        });
    }

    // private methods for player's item functionality
    private int countItems(Class<? extends Item> type) {
        int count = 0;
        for (Item item : player.getInventory()) {
            if (type.isInstance(item)) {
                ++count;
            }
        }
        return count;
    }

    private void useItemByType(Class<? extends Item> type) {
        Item toUse = null;
        for (Item item : player.getInventory()) {
            if (type.isInstance(item)) {
                toUse = item;
                break ;
            }
        }
        if (toUse != null) {
            StringBuilder log = new StringBuilder();
            log.append(player.useItem(toUse)).append("\n"); //

            log.append(handleEnemyTurn());

            combatLog.setText(log.toString());
            isItemMenuOpen = false;
            refreshUI();
            checkBattleStatus();
        }
    }
}
