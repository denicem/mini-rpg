package com.minirpg.game.controller;

import com.minirpg.game.util.*;
import javafx.animation.Animation;
import javafx.animation.Timeline;
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

    private final StoryManager sm = new StoryManager();
    private String[] chunks;
    private int chunkIndex = 0;
    private Timeline textAnimation;

    @FXML
    public void initialize() {
        Helper.logCall(this);

        String charPath = GameSession.getCharacterImgPath();
        Helper.loadImage(playerImageView, charPath);

        System.out.printf("ACT %d.\n", GameSession.getCurrentAct());
        this.exitButton.managedProperty().bind(this.exitButton.visibleProperty());
        Helper.loadImage(playerImageView, GameSession.getCharacterImgPath());

        loadAct(GameSession.getCurrentAct());
    }

    private void loadAct(int act) {
        Helper.loadImage(gameSceneView, sm.getBackgroundForAct(act));

        this.chunks = sm.getStoryChunks(act);
        this.chunkIndex = 0;

        // Hintergrund basierend auf Akt setzen
        Helper.loadImage(gameSceneView, sm.getBackgroundForAct(act));

        if (act == StoryManager.ACT_4 || act == StoryManager.ACT_5) {
            npcImageView.setVisible(true);
            Helper.loadImage(npcImageView, Assets.CH_PRINCESS);
        } else {
            npcImageView.setVisible(false);
        }

        refreshUI();
    }

    private void refreshUI() {
        this.textAnimation = Helper.animateText(storyText, chunks[chunkIndex]);
        int act = GameSession.getCurrentAct();
        boolean isLastChunk = (chunkIndex == chunks.length - 1);

        if (!isLastChunk) {
            // Während man durch Chunks blättert
            continueButton.setText("Continue");
            exitButton.setVisible(false);
        } else {
            // Logik für den Abschluss eines Aktes laut Story.txt
            exitButton.setVisible(true);
            switch (act) {
                case StoryManager.ACT_1 -> { continueButton.setText(sm.getButtonText(act, 1)); exitButton.setText(sm.getButtonText(act, 2)); }
                case StoryManager.ACT_2 -> { continueButton.setText(sm.getButtonText(act, 1)); exitButton.setText(sm.getButtonText(act, 2)); }
                case StoryManager.ACT_3 -> { continueButton.setText(sm.getButtonText(act, 1)); exitButton.setText(sm.getButtonText(act, 2)); }
                case StoryManager.ACT_4 -> { continueButton.setText(sm.getButtonText(act, 1)); exitButton.setText(sm.getButtonText(act, 2)); }
                case StoryManager.ACT_5 -> { continueButton.setText(sm.getButtonText(act, 1)); exitButton.setVisible(false); }
            }
        }
    }

    @FXML
    protected void onContinueButtonClick() {
       if (textAnimation != null && textAnimation.getStatus() == Animation.Status.RUNNING) {
           textAnimation.stop();
           storyText.setText(chunks[chunkIndex]);
           return;
       }

        if (chunkIndex < chunks.length - 1) {
            chunkIndex++;
            refreshUI();
        } else {
            handleActTransition();
        }
    }

    private void handleActTransition() {
        int act = GameSession.getCurrentAct();
        switch (act) {
            case StoryManager.ACT_1, StoryManager.ACT_4 -> {
                GameSession.nextAct();
                loadAct(GameSession.getCurrentAct());
            }
            case StoryManager.ACT_2, StoryManager.ACT_3, StoryManager.ACT_5 -> {
                if (act == StoryManager.ACT_2) GameSession.nextAct();
                ViewManager.switchTo("combat-view.fxml");
            }
        }
    }

    @FXML
    protected void onExitButtonClick() {
        if (GameSession.getCurrentAct() == StoryManager.ACT_3) {
            GameSession.nextAct();
            loadAct(GameSession.getCurrentAct());
        } else {
            showEnding(StoryManager.Ending.COWARD);
        }
    }

    private void showEnding(StoryManager.Ending ending) {
        GameSession.setFinalEnding(ending);
        ViewManager.switchTo("end-screen-view.fxml");
    }
}
