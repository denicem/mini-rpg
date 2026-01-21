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

    private final StoryManager sm = new StoryManager();

    @FXML
    public void initialize() {
        Helper.logCall(this);

        Helper.loadImage(backgroundView, Assets.BG_ENDING);

        displayEnding();
    }

    private void displayEnding() {
        StoryManager.Ending ending = GameSession.getFinalEnding();
        if (ending == null) return;

        subtitleLabel.setText(sm.getEndingText(ending));

        switch (ending) {
            case GOOD -> {
                titleLabel.setText("VICTORY!");
                Helper.loadImage(characterView, Assets.CH_KNIGHT_WITH_SWORD_AND_SHIELD);
            }
            case BAD -> {
                titleLabel.setText("GAME OVER");
                Helper.loadImage(characterView, Assets.CH_KNIGHT_SLAIN);
            }
            case COWARD -> {
                titleLabel.setText("RETIRED");
                Helper.loadImage(characterView, Assets.CH_KNIGHT);
            }
            case RETREAT -> {
                titleLabel.setText("ESCAPE!");
                Helper.loadImage(characterView, Assets.CH_KNIGHT);
            }
        }
    }

    @FXML
    private void onCloseButtonClick() {
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }
}
