package com.minirpg.game.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import com.minirpg.game.model.Player;
import com.minirpg.game.util.Assets;
import com.minirpg.game.util.GameSession;
import com.minirpg.game.util.Helper;
import com.minirpg.game.util.ViewManager;

public class CharacterSelectionController {
    @FXML private ImageView backgroundImage;
    @FXML private ImageView knightView;
    @FXML private ImageView knightGirlView;

    @FXML
    public void initialize() {
        Helper.logCall(this);

        Helper.loadImage(backgroundImage, Assets.BG_FOREST);
        Helper.loadImage(knightView, Assets.CH_KNIGHT);
        Helper.loadImage(knightGirlView, Assets.CH_KNIGHT_GIRL);
    }

    @FXML
    private void onKnightSelected() {
        System.out.println("Knight selected!");

        // GameSession starten und Player erstellen
        GameSession.startNewGame("Sir Alistair");
        GameSession.setSelectedCharacterType("knight"); // Typ als "knight" speichern
        ViewManager.switchTo("story-view.fxml");
    }

    @FXML
    private void onKnightGirlSelected() {
        System.out.println("Knight Girl selected!");

        // GameSession starten und Player erstellen
        GameSession.startNewGame("Lady Lala");
        GameSession.setSelectedCharacterType("knight_girl"); // Typ als "knight_girl" speichern
        ViewManager.switchTo("story-view.fxml");
    }

}
