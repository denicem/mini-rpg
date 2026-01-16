package com.minirpg.game.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.minirpg.game.util.Assets;

import java.util.Objects;

public class MainMenuController {

        @FXML
        private ImageView backgroundImage;

        @FXML
        private void initialize() {
            backgroundImage.setImage(
                    new Image(
                            Objects.requireNonNull(getClass().getResourceAsStream(Assets.BG_START_SCREEN))
                    )
            );
        }

        @FXML
        private void onStartClicked() {
            ViewManager.switchTo("story-view.fxml");
        }

        @FXML
        private void onExitClicked() {
            System.exit(0);
        }
    }
}
