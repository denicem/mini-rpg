package com.minirpg.game.controller;

import com.minirpg.game.model.*;
import com.minirpg.game.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class CombatController {
    @FXML private ImageView backgroundView;
    @FXML private ImageView playerImageView;
    @FXML private ImageView enemyImageView;
    @FXML private Label combatLog;
    @FXML private Label playerHpLabel;
    @FXML private Label enemyHpLabel;
    @FXML private Label playerAtkDefLabel;
    @FXML private Label enemyAtkDefLabel;
    @FXML private Button doNothingButton;
    @FXML private Button attackButton;
    @FXML private Button potionButton;
    @FXML private Button fleeButton;

    private Player player;
    private Enemy enemy;

    private StoryManager sm = new StoryManager();
    private boolean isCombatOver = false;
    private boolean isItemMenuOpen = false;

    @FXML
    public void initialize() {
        this.player = GameSession.getPlayer();
        this.enemy = GameSession.getCurrentEnemy();

        // Bilder laden
        Helper.loadImage(backgroundView, Assets.BG_DARK_FOREST);
        Helper.loadImage(playerImageView, GameSession.getCharacterImgPath());
        loadEnemyImage();

        // call on. setVisible() from these buttons -> automatically triggers .setManaged() as well w the same value
        potionButton.managedProperty().bind(potionButton.visibleProperty());
        doNothingButton.managedProperty().bind(doNothingButton.visibleProperty());
        fleeButton.managedProperty().bind(fleeButton.visibleProperty());

        // UI initialisieren
        refreshUI();
        combatLog.setText("A wild " + enemy.getName() + " blocks your path!");
    }

    private void loadEnemyImage() {
        if (enemy instanceof Mage) Helper.loadImage(enemyImageView, Assets.CH_MAGE);
        else if (enemy instanceof Elf) Helper.loadImage(enemyImageView, Assets.CH_ELF);
        else if (enemy instanceof Dragon) {
            Helper.loadImage(enemyImageView, Assets.CH_DRAGON);
            Helper.loadImage(backgroundView, Assets.BG_CASTLE_INSIDE);
        }
    }

    private void updateStatsDisplay() {
        // HP Anzeigen
        playerHpLabel.setText("HP: " + player.getHp() + "/" + player.getMaxHp());
        enemyHpLabel.setText("HP: " + enemy.getHp() + "/" + enemy.getMaxHp());

        // ATK & DEF Anzeigen
        playerAtkDefLabel.setText("ATK: " + player.getStats().getAtk() + " | DEF: " + player.getStats().getDef());
        enemyAtkDefLabel.setText("ATK: " + enemy.getStats().getAtk() + " | DEF: " + enemy.getStats().getDef());
    }

    @FXML
    private void onDoNothingButtonClick() {
        if (isItemMenuOpen) {
            useItemByType(StrengthPotion.class);
            return ;
        }
        StringBuilder turnLog = new StringBuilder();
        // 2. Prüfen, ob der Gegner noch lebt
        if (enemy.isAlive()) {
            // 3. Gegner schlägt zurück!
            String enemyAttackResult = BattleSystem.performAttack(enemy, player);
            turnLog.append(enemyAttackResult);
        }

        // UI aktualisieren
        combatLog.setText(turnLog.toString());
        updateStatsDisplay();

        // 4. Prüfen, ob jemand besiegt wurde
        checkBattleStatus();
    }

    @FXML
    private void onFleeButtonClick() {
        if (isItemMenuOpen) {
            useItemByType(IronCharm.class);
            return ;
        }
        this.combatLog.setText("You really wanna flee now? Bruh... NO!");
    }

    @FXML
    private void onAttackButtonClick() {
        if (isCombatOver) {
            GameSession.setCurrentAct(StoryManager.ACT_3);
            ViewManager.switchTo("story-view.fxml");
            return ;
        }
        if (isItemMenuOpen) {
            useItemByType(Potion.class);
            return ;
        }
        StringBuilder turnLog = new StringBuilder();

        // 1. Spieler greift an
        String playerAttackResult = BattleSystem.performAttack(player, enemy);
        turnLog.append(playerAttackResult).append("\n");

        // 2. Prüfen, ob der Gegner noch lebt
        if (enemy.isAlive()) {
            // 3. Gegner schlägt zurück!
            String enemyAttackResult = BattleSystem.performAttack(enemy, player);
            turnLog.append(enemyAttackResult);
        }

        // UI aktualisieren
        combatLog.setText(turnLog.toString());
        refreshUI();

        // 4. Prüfen, ob jemand besiegt wurde
        checkBattleStatus();
    }

    private void checkBattleStatus() {
        if (!player.isAlive()) {
            GameSession.setFinalEnding(StoryManager.Ending.BAD); //
            ViewManager.switchTo("end-screen-view.fxml");
        } else if (!enemy.isAlive()) {
            handleVictory();
        }
    }

    private void handleVictory() {
        if (enemy instanceof Dragon) {
            GameSession.setFinalEnding(StoryManager.Ending.GOOD);
            ViewManager.switchTo("end-screen-view.fxml");
        } else {
            this.player.addItem(new Potion());
            this.player.addItem(new StrengthPotion());
            this.player.addItem(new IronCharm());
            // 1. Loot-Nachricht aus dem StoryManager holen
            // Fürs Erste nehmen wir eine Potion als Beispiel-Loot
            String lootMessage = sm.getLootPickupText(StoryManager.ItemType.POTION_HP);
            System.out.println(lootMessage);
            combatLog.setText(lootMessage);
            isCombatOver = true;
            refreshUI();
        }
    }

    private Potion findPotionInInventory() {
        for (Item item : player.getInventory()) {
            if (item instanceof Potion) {
                return (Potion) item;
            }
        }
        return null;
    }

    @FXML
    private void onPotionButtonClick() {
        if (isItemMenuOpen)
            isItemMenuOpen = false;
        else
            isItemMenuOpen = true;
        this.refreshUI();
    }

    private long countItems(Class<? extends Item> type) {
        return player.getInventory().stream()
                .filter(type::isInstance)
                .count();
    }

    private void refreshUI() {
        updateStatsDisplay();

        if (isCombatOver) {
            attackButton.setText("Go on");
            potionButton.setVisible(false);
            doNothingButton.setVisible(false);
            fleeButton.setVisible(false);
        }
        else if (isItemMenuOpen) {
            attackButton.setText(String.format("HP Potion (%d)", countItems(Potion.class)));
            doNothingButton.setText(String.format("ATK Buff (%d)", countItems(StrengthPotion.class)));
            fleeButton.setText(String.format("DEF Buff (%d)", countItems(IronCharm.class)));
            potionButton.setText("BACK");

            attackButton.setDisable(countItems(Potion.class) == 0);
            doNothingButton.setDisable(countItems(StrengthPotion.class) == 0);
            fleeButton.setDisable(countItems(IronCharm.class) == 0);
        }
        else {
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

    private void useItemByType(Class<? extends Item> type) {
        Item toUse = player.getInventory().stream()
                .filter(type::isInstance)
                .findFirst()
                .orElse(null);
        if (toUse != null) {
            StringBuilder log = new StringBuilder();
            log.append(player.useItem(toUse)).append("\n"); //

            if (enemy.isAlive()) {
                log.append(BattleSystem.performAttack(enemy, player)); //
            }

            combatLog.setText(log.toString());
            isItemMenuOpen = false;
            refreshUI();
            checkBattleStatus();
        }
    }
}
