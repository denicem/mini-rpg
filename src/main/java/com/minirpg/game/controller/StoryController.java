package com.minirpg.game.controller;

import com.minirpg.game.model.Dragon;
import com.minirpg.game.model.Elf;
import com.minirpg.game.model.Enemy;
import com.minirpg.game.model.Mage;
import com.minirpg.game.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class StoryController {
    @FXML private ImageView gameSceneView;
    @FXML private ImageView playerImageView;
    @FXML private Label storyText;
    @FXML private Button continueButton;
    @FXML private Button exitButton;

    private StoryManager sm = new StoryManager();
    private String[] chunks;
    private int chunkIndex = 0;
    private boolean isCombatPhase = false;

    private int forestCombatStage = 0;

    @FXML
    public void initialize() {
        // Spielerbild laden
        Helper.loadImage(playerImageView, GameSession.getCharacterImgPath());
        // Start mit Akt 1
//        loadAct(StoryManager.ACT_1);
        loadAct(GameSession.getCurrentAct());
    }

    private void loadAct(int act) {
        GameSession.setCurrentAct(act);
        this.chunks = sm.getStoryChunks(act);
        this.chunkIndex = 0;
        this.isCombatPhase = false;

        // Hintergrund basierend auf Akt setzen
        Helper.loadImage(gameSceneView, sm.getBackgroundForAct(act));
        displayChunk();
    }

    private void displayChunk() {
        storyText.setText(chunks[chunkIndex]);
        updateButtons();
    }

    private void updateButtons() {
        int act = GameSession.getCurrentAct();
        boolean isLastChunk = (chunkIndex == chunks.length - 1);

        if (!isLastChunk) {
            // Während man durch Chunks blättert [cite: 19, 21]
            continueButton.setText("Continue");
            exitButton.setVisible(false);
        } else {
            // Logik für den Abschluss eines Aktes laut Story.txt [cite: 18-34]
            exitButton.setVisible(true);
            switch (act) {
                case StoryManager.ACT_1 -> {
                    continueButton.setText("Continue");
                    exitButton.setText("Flee");
                }
                case StoryManager.ACT_2 -> {
                    continueButton.setText("Fight");
                    exitButton.setText("Flee");
                }
                case StoryManager.ACT_3 -> { // Training Loop Entscheidung [cite: 26]
                    continueButton.setText("Yes");
                    exitButton.setText("No");
                }
                case StoryManager.ACT_4 -> {
                    continueButton.setText("Continue");
                    exitButton.setText("Flee");
                }
                case StoryManager.ACT_5 -> {
                    continueButton.setText("Fight");
                    exitButton.setVisible(false); // Finale: Nur Kämpfen [cite: 32]
                }
            }
        }
    }

    @FXML
    protected void onContinueButtonClick() {
        if (isCombatPhase) {
            handleCombatResult(true); // Platzhalter für Sieg
            return;
        }

        if (chunkIndex < chunks.length - 1) {
            chunkIndex++;
            displayChunk();
        } else {
            handleActTransition();
        }
    }

    private void handleActTransition() {
        int act = GameSession.getCurrentAct();
        switch (act) {
            case StoryManager.ACT_1 -> loadAct(StoryManager.ACT_2);
            case StoryManager.ACT_2 -> {
                GameSession.setCurrentEnemy(new Elf());
                ViewManager.switchTo("combat-view.fxml");
            }
            case StoryManager.ACT_3 -> {
                Enemy enemy = (Math.random() > 0.5 ? new Mage() : new Elf());
                GameSession.setCurrentEnemy(enemy);
                ViewManager.switchTo("combat-view.fxml");
            }
            case StoryManager.ACT_4 -> loadAct(StoryManager.ACT_5);
            case StoryManager.ACT_5 -> enterFakeCombat(false); // Drachenkampf
        }
    }

    @FXML
    protected void onExitButtonClick() {
        if (isCombatPhase) {
            handleCombatResult(false); // Platzhalter für Niederlage
            return;
        }

        int act = GameSession.getCurrentAct();
        if (act == StoryManager.ACT_3) {
            // "No" gewählt -> Weiter zum Schloss
            loadAct(StoryManager.ACT_4);
        } else {
            // Alle anderen Flee-Optionen führen zum Coward Ending
            showEnding(StoryManager.Ending.COWARD);
        }
    }

    private void enterFakeCombat(boolean randomEnemy) {
        isCombatPhase = true;
        String intro;
        if (randomEnemy) {
            // Zufälliger Gegner Intro-Text
            boolean isMage = Math.random() > 0.5;
            intro = sm.getEnemyIntroText(isMage ? StoryManager.EnemyType.FOREST_MAGE : StoryManager.EnemyType.DRAMATIC_ELF);
        } else {
            intro = "THE FINAL TRIAL BEGINS!";
        }

        storyText.setText(intro);
        continueButton.setText("[WIN]");
        exitButton.setText("[LOSE]");
        exitButton.setVisible(true);
    }

    private void handleCombatResult(boolean win) {
        if (!win) {
            showEnding(StoryManager.Ending.BAD);
            return;
        }

        if (GameSession.getCurrentAct() == StoryManager.ACT_5) {
            showEnding(StoryManager.Ending.GOOD); //
        } else {
            // Sieg im Wald -> Loot -> Frage nach Training
            storyText.setText(sm.getLootPickupText(StoryManager.ItemType.POTION_HP));
            continueButton.setText("Next");
            exitButton.setVisible(false);
            isCombatPhase = false;

            // Bereite Akt 3 (Die Frage) vor
            GameSession.setCurrentAct(StoryManager.ACT_3);
            this.chunks = sm.getStoryChunks(StoryManager.ACT_3);
            this.chunkIndex = -1; // Nächster Klick zeigt ersten Chunk von Akt 3
        }
    }

    private void showEnding(StoryManager.Ending ending) {
        // 1. Ende in der Session speichern
        GameSession.setFinalEnding(ending);

        // 2. Zur Endscreen-View wechseln
        ViewManager.switchTo("end-screen-view.fxml");
    }
}