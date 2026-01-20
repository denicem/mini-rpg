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
    private boolean isItemMenuOpen = false;

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
        refreshButtons();
//        updatePotionButton();
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
            useItemByType(IronCharm.class);
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
            isItemMenuOpen = false;
            refreshButtons();
            return ;
        }
        this.combatLog.setText("You really wanna flee now? Bruh... NO!");
    }

    @FXML
    private void onAttackButtonClick() {
        if (isItemMenuOpen) {
            useItemByType(StrengthPotion.class);
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
            this.player.addItem(new StrengthPotion());
            this.player.addItem(new IronCharm());
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
        if (isItemMenuOpen) {
            useItemByType(Potion.class);
            return ;
        }
        this.isItemMenuOpen = true;
        this.refreshButtons();
//        Potion potion = findPotionInInventory();
//
//        if (potion != null) {
//            StringBuilder turnLog = new StringBuilder();
//
//            // 1. Spieler nutzt den Trank
//            String useMsg = player.useItem(potion);
//            turnLog.append(useMsg).append("\n");
//
//            // 2. Der Gegner nutzt deine Heilpause für einen Angriff!
//            if (enemy.isAlive()) {
//                String enemyAttackResult = BattleSystem.performAttack(enemy, player);
//                turnLog.append(enemyAttackResult);
//            }
//
//            // UI aktualisieren
//            combatLog.setText(turnLog.toString());
//            updateStatsDisplay();
//            updatePotionButton();
//            checkBattleStatus();
//        }
    }

    private long countItems(Class<? extends Item> type) {
        return player.getInventory().stream()
                .filter(type::isInstance)
                .count();
    }

    // Methode, die das Aussehen der Buttons umschaltet
    private void refreshButtons() {
        if (!isItemMenuOpen) {
            // Hauptmenü Zustand
            attackButton.setText("ATTACK!");
            doNothingButton.setText("DO NOTHING...");
            potionButton.setText("ITEMS"); // Umbenannt von "USE POTION"
            fleeButton.setText("FLEE");

            // Buttons wieder aktivieren (falls sie im Item-Menü leer waren)
            attackButton.setDisable(false);
            doNothingButton.setDisable(false);
            potionButton.setDisable(false);
        } else {
            // Item-Untermenü Zustand
            long hpCount = countItems(Potion.class);
            long atkCount = countItems(StrengthPotion.class);
            long defCount = countItems(IronCharm.class);

            attackButton.setText("ATK Buff (" + atkCount + ")");
            attackButton.setDisable(atkCount == 0);

            doNothingButton.setText("DEF Buff (" + defCount + ")");
            doNothingButton.setDisable(defCount == 0);

            potionButton.setText("HP Potion (" + hpCount + ")");
            potionButton.setDisable(hpCount == 0);

            fleeButton.setText("BACK");
            fleeButton.setDisable(false);
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
            isItemMenuOpen = false; // Zurück zum Hauptmenü nach Benutzung
            updateStatsDisplay();
            refreshButtons();
            checkBattleStatus();
        }
    }
}
