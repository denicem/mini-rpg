package com.minirpg.game.controller;

import com.minirpg.game.model.*;
import com.minirpg.game.util.*;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StoryController {
    @FXML private ImageView gameSceneView;
    @FXML private ImageView playerImageView;
    @FXML private Label storyText;
    @FXML private Button choiceButton;
    @FXML private Button exitButton;

    private StoryManager sm = new StoryManager();
    private String[] chunks = new String[0];
    private int chunkIndex = 0;
    private boolean isShowingChunks = false;

    @FXML
    public void initialize() {
        // 1. Charakterbild laden
        Helper.loadImage(playerImageView, GameSession.getCharacterImgPath());

        // 2. Start-Akt laden (Meistens Akt 1 nach der Auswahl)
        updateSceneForAct(GameSession.getCurrentAct());
    }

    /**
     * Aktualisiert Hintergrund und Text basierend auf dem Akt.
     */
    private void updateSceneForAct(int act) {
        // Hintergrund setzen
        switch (act) {
            case 1 -> Helper.loadImage(gameSceneView, Assets.BG_CASTLE_INFRONT);
            case 2 -> Helper.loadImage(gameSceneView, Assets.BG_FOREST);
            case 3 -> Helper.loadImage(gameSceneView, Assets.BG_CASTLE_INFRONT); // Wieder am Schloss
            case 4 -> Helper.loadImage(gameSceneView, Assets.BG_CASTLE_INSIDE);
            default -> Helper.loadImage(gameSceneView, Assets.BG_FOREST);
        }

        // Text-Chunks vom StoryManager holen
        startChunks(sm.getStoryChunks(act));

        // Button-Text anpassen
        choiceButton.setText("Continue");
        exitButton.setVisible(act == 1); // Exit nur am Anfang zeigen
    }

    @FXML
    protected void onContinueButtonClick() {
        // 1. Wenn noch Text in der "Warteschlange" ist, erst diesen zeigen
        if (advanceChunk()) return;

        // 2. Wenn der Text fertig ist, entscheiden was passiert
        int act = GameSession.getCurrentAct();

        switch (act) {
            case 1 -> { // Nach Intro in den Wald
                GameSession.setCurrentAct(2);
                updateSceneForAct(2);
            }
            case 2 -> { // Im Wald erscheint der Magier
                GameSession.setCurrentEnemy(new Mage());
                ViewManager.switchTo("combat-view.fxml");
            }
            case 3 -> { // Nach Mage/Elf Sieg -> Zum Schloss
                GameSession.setCurrentAct(4);
                updateSceneForAct(4);
            }
            case 4 -> { // Im Schloss erscheint der Drache
                GameSession.setCurrentEnemy(new Dragon());
                ViewManager.switchTo("combat-view.fxml");
            }
        }
    }

    @FXML
    protected void onExitButtonClick() {
        storyText.setText(sm.getCowardText());
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> ((Stage) storyText.getScene().getWindow()).close());
        pause.play();
    }

    // --- Hilfsmethoden für das Text-System ---

    private void startChunks(String[] newChunks) {
        this.chunks = (newChunks != null) ? newChunks : new String[]{""};
        this.chunkIndex = 0;
        this.isShowingChunks = chunks.length > 1;
        storyText.setText(chunks[0]);
    }

    private boolean advanceChunk() {
        if (!isShowingChunks) return false;

        if (chunkIndex < chunks.length - 1) {
            chunkIndex++;
            storyText.setText(chunks[chunkIndex]);
            return true;
        } else {
            isShowingChunks = false;
            return false;
        }
    }
}