package com.minirpg.game.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.minirpg.game.util.Assets;
import javafx.stage.Stage;

import java.util.Objects;

public class MainMenuController {

    @FXML
    private ImageView backgroundImage;
    @FXML private ImageView startBtnImage; // Neu für das Button-Bild
    @FXML private ImageView exitBtnImage;  // Neu für das Button-Bild

    @FXML
    private void initialize() {
        System.out.println(Assets.BG_START_SCREEN);
        this.setView(Assets.BG_START_SCREEN);

        // Button-Grafiken laden
        this.startBtnImage.setImage(new Image(getClass().getResourceAsStream(Assets.UI_START_BUTTON)));
        this.exitBtnImage.setImage(new Image(getClass().getResourceAsStream(Assets.UI_EXIT_BUTTON)));
    }

    @FXML
    private void onStartClicked() {
        ViewManager.switchTo("story-view.fxml"); //game-view?
    }

    @FXML
    private void onExitClicked() {
        System.exit(0);
    }

    private void setView(String imagePath) {
        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            this.backgroundImage.setImage(image);
        } catch (Exception e) {
            System.err.println("Error loading image: " + imagePath);
            exitAndCloseWindow();
        }
    }

    private void exitAndCloseWindow() {
        Stage stage = (Stage) this.backgroundImage.getScene().getWindow();
        stage.close();
    }
}
