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

        boolean isGirl = GameSession.getSelectedCharacterType().contains("knight_girl");

        Helper.loadImage(backgroundView, storyManager.getBackgroundForEnding(ending));

        titleLabel.setText(StoryManager.getEndingTitle(ending));
        Helper.animateText(subtitleLabel, storyManager.getEndingText(ending));

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