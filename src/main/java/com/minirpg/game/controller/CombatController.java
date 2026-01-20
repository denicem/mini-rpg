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

    @FXML
    public void initialize() {
        this.player = GameSession.getPlayer();
        this.enemy = GameSession.getCurrentEnemy();

        // Bilder laden
        Helper.loadImage(backgroundView, Assets.BG_DARK_FOREST);
        Helper.loadImage(playerImageView, GameSession.getCharacterImgPath());
        loadEnemyImage();

        // UI initialisieren
        updateStatsDisplay();
        updatePotionButton();
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
        this.combatLog.setText("You really wanna flee now? Bruh... NO!");
    }

    @FXML
    private void onAttackButtonClick() {
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
        updateStatsDisplay();

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
            Potion reward = new Potion();
            this.player.addItem(reward);
            // 1. Loot-Nachricht aus dem StoryManager holen
            // Fürs Erste nehmen wir eine Potion als Beispiel-Loot
            String lootMessage = sm.getLootPickupText(StoryManager.ItemType.POTION_HP);
            System.out.println(lootMessage);
            combatLog.setText(lootMessage);
            this.updatePotionButton();

            // 2. Den Button umfunktionieren, damit der Spieler aktiv weiterklickt
            attackButton.setText("Collect Loot & Continue");
            attackButton.setOnAction(e -> {
                // Zurück zur Story (Akt 3: Training-Entscheidung)
                GameSession.setCurrentAct(StoryManager.ACT_3);
                ViewManager.switchTo("story-view.fxml");
            });
            doNothingButton.setDisable(true);
            potionButton.setDisable(true);
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

    private void updatePotionButton() {
        int count = this.player.getInventory().size();
        potionButton.setText("USE POTION (" + count + ")");
        potionButton.setDisable(count == 0); // Button deaktivieren, wenn keine Tränke da sind
    }

    @FXML
    private void onPotionButtonClick() {
        Potion potion = findPotionInInventory();

        if (potion != null) {
            StringBuilder turnLog = new StringBuilder();

            // 1. Spieler nutzt den Trank
            String useMsg = player.useItem(potion);
            turnLog.append(useMsg).append("\n");

            // 2. Der Gegner nutzt deine Heilpause für einen Angriff!
            if (enemy.isAlive()) {
                String enemyAttackResult = BattleSystem.performAttack(enemy, player);
                turnLog.append(enemyAttackResult);
            }

            // UI aktualisieren
            combatLog.setText(turnLog.toString());
            updateStatsDisplay();
            updatePotionButton();
            checkBattleStatus();
        }
    }
}
