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
    @FXML private ImageView npcImageView;
    @FXML private Label storyText;
    @FXML private Button continueButton;
    @FXML private Button exitButton;

    private StoryManager sm = new StoryManager();
    private String[] chunks;
    private int chunkIndex = 0;

    @FXML
    public void initialize() {
        Helper.loadImage(playerImageView, GameSession.getCharacterImgPath());
        loadAct(GameSession.getCurrentAct());
    }

    private void loadAct(int act) {
        GameSession.setCurrentAct(act);
        this.chunks = sm.getStoryChunks(act);
        this.chunkIndex = 0;

        // Hintergrund basierend auf Akt setzen
        Helper.loadImage(gameSceneView, sm.getBackgroundForAct(act));

        if (act == StoryManager.ACT_4) {
            npcImageView.setVisible(true);
            Helper.loadImage(npcImageView, Assets.CH_PRINCESS);
        } else {
            npcImageView.setVisible(false);
        }

        this.displayChunk();
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
            case StoryManager.ACT_5 -> {
                GameSession.setCurrentEnemy(new Dragon());
                ViewManager.switchTo("combat-view.fxml");
            }
        }
    }

    @FXML
    protected void onExitButtonClick() {
        int act = GameSession.getCurrentAct();
        if (act == StoryManager.ACT_3) {
            // "No" gewählt -> Weiter zum Schloss
            loadAct(StoryManager.ACT_4);
        } else {
            // Alle anderen Flee-Optionen führen zum Coward Ending
            showEnding(StoryManager.Ending.COWARD);
        }
    }

    private void showEnding(StoryManager.Ending ending) {
        // 1. Ende in der Session speichern
        GameSession.setFinalEnding(ending);

        // 2. Zur Endscreen-View wechseln
        ViewManager.switchTo("end-screen-view.fxml");
    }
}