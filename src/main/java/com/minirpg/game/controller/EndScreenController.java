package com.minirpg.game.controller;

import com.minirpg.game.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class EndScreenController {
    @FXML private ImageView backgroundView;
    @FXML private ImageView characterView;
    @FXML private Label titleLabel;
    @FXML private Label subtitleLabel;

    @FXML
    public void initialize() {
        StoryManager.Ending ending = GameSession.getFinalEnding();

        //If Knight_Girl
        boolean isGirl = "knight_girl".equals(GameSession.getSelectedCharacterType());

        // Standard-Hintergrund für Enden
        Helper.loadImage(backgroundView, Assets.BG_ENDING);

        switch (ending) {
            case GOOD -> {
                titleLabel.setText("VICTORY!");
                Helper.animateText(subtitleLabel, "The Dragon is slain. You are a true Knight!");
                Helper.loadImage(characterView, isGirl ? Assets.CH_KNIGHT_GIRL : Assets.CH_KNIGHT_WITH_SWORD_AND_SHIELD);
            }
            case BAD -> {
                titleLabel.setText("GAME OVER");
                Helper.animateText(subtitleLabel,"Your journey ends here. You've become a dragon's snack.");
                Helper.loadImage(characterView, isGirl ? Assets.CH_KNIGHT_GIRL : Assets.CH_KNIGHT_SLAIN);
            }
            case COWARD -> {
                titleLabel.setText("RETIRED");
                Helper.animateText(subtitleLabel,"A warm bed and stew... but you'll always hear that whisper: 'Coward'.");
                Helper.loadImage(characterView, isGirl ? Assets.CH_KNIGHT_GIRL : Assets.CH_KNIGHT); // Normales Bild
                Helper.loadImage(backgroundView, Assets.BG_AT_HOME);
            }
            case RETREAT -> {
                titleLabel.setText("ESCAPE!");
                Helper.animateText(subtitleLabel,"You sprinted away! Title earned: 'Knight of Swift Retreat'.");
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
