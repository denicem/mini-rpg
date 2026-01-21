package com.minirpg.game.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import com.minirpg.game.util.Assets;
import com.minirpg.game.util.GameSession;
import com.minirpg.game.util.Helper;
import com.minirpg.game.util.StoryManager;

public class EndScreenController {
    @FXML private ImageView backgroundView;
    @FXML private ImageView characterView;
    @FXML private Label titleLabel;
    @FXML private Label subtitleLabel;

    private final StoryManager storyManager = new StoryManager();

    @FXML
    public void initialize() {
        Helper.logCall(this);

        StoryManager.Ending ending = GameSession.getFinalEnding();
        if (ending == null) return;

        // Prüfen, ob weiblicher Ritter gewählt wurde
        boolean isGirl = "knight_girl".equals(GameSession.getSelectedCharacterType());

        // 1. Hintergrund aus StoryManager setzen
        // (Ersetzt die manuelle Logik für BG_AT_HOME bei COWARD)
        Helper.loadImage(backgroundView, storyManager.getBackgroundForEnding(ending));

        // 2. Texte aus StoryManager setzen
        titleLabel.setText(StoryManager.getEndingTitle(ending));
        Helper.animateText(subtitleLabel, storyManager.getEndingText(ending));

        // 3. Charakterbild setzen (Logik bleibt inline wie gewünscht)
        switch (ending) {
            case GOOD -> {
                Helper.loadImage(characterView, isGirl ? Assets.CH_KNIGHT_GIRL : Assets.CH_KNIGHT_WITH_SWORD_AND_SHIELD);
            }
            case BAD -> {
                Helper.loadImage(characterView, isGirl ? Assets.CH_KNIGHT_GIRL : Assets.CH_KNIGHT_SLAIN);
            }
            case COWARD, RETREAT -> {
                Helper.loadImage(characterView, isGirl ? Assets.CH_KNIGHT_GIRL : Assets.CH_KNIGHT);
            }
        }
    }

    @FXML
    private void onCloseButtonClick() {
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }
}