package com.minirpg.game.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import com.minirpg.game.util.Assets;
import com.minirpg.game.util.Helper;
import com.minirpg.game.util.ViewManager;

public class MainMenuController {
    @FXML private ImageView backgroundImage;
    @FXML private ImageView startBtnImage;
    @FXML private ImageView exitBtnImage;

    @FXML
    private void initialize() {
        Helper.logCall(this);

        Helper.loadImage(backgroundImage, Assets.BG_START_SCREEN);
        Helper.loadImage(startBtnImage, Assets.UI_START_BUTTON);
        Helper.loadImage(exitBtnImage, Assets.UI_EXIT_BUTTON);
    }

    @FXML
    private void onStartClicked() {
        ViewManager.switchTo("character-selection-view.fxml");
    }

    @FXML
    private void onExitClicked() {
        System.exit(0);
    }
}
