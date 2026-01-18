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

        // Standard-Hintergrund für Enden
        Helper.loadImage(backgroundView, Assets.BG_ENDING);

        switch (ending) {
            case GOOD -> {
                titleLabel.setText("VICTORY!");
                subtitleLabel.setText("The Dragon is slain. You are a true Knight!");
                Helper.loadImage(characterView, Assets.CH_KNIGHT_WITH_SWORD_AND_SHIELD);
            }
            case BAD -> {
                titleLabel.setText("GAME OVER");
                subtitleLabel.setText("Your journey ends here. You've become a dragon's snack.");
                Helper.loadImage(characterView, Assets.CH_KNIGHT_SLAIN);
            }
            case COWARD -> {
                titleLabel.setText("RETIRED");
                subtitleLabel.setText("A warm bed and stew... but you'll always hear that whisper: 'Coward'.");
                Helper.loadImage(characterView, Assets.CH_KNIGHT); // Normales Bild
            }
            case RETREAT -> {
                titleLabel.setText("ESCAPE!");
                subtitleLabel.setText("You sprinted away! Title earned: 'Knight of Swift Retreat'.");
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
