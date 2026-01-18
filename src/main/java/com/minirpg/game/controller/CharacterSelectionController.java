package com.minirpg.game.controller;

import com.minirpg.game.model.Player;
import com.minirpg.game.util.Assets;
import com.minirpg.game.util.GameSession;
import com.minirpg.game.util.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CharacterSelectionController {
    @FXML private ImageView backgroundImage;
    @FXML private ImageView knightView;
    @FXML private ImageView knightGirlView;

    @FXML
    public void initialize() {
        // Hintergrund laden
        backgroundImage.setImage(new Image(getClass().getResourceAsStream(Assets.BG_FOREST)));

        // Charakter-Vorschauen laden
        knightView.setImage(new Image(getClass().getResourceAsStream(Assets.CH_KNIGHT)));
        knightGirlView.setImage(new Image(getClass().getResourceAsStream(Assets.CH_KNIGHT_GIRL)));
    }

    @FXML
    private void onKnightSelected() {
        System.out.println("Knight selected!");

        GameSession.setSelectedCharacterType("knight"); // Typ als "knight" speichern
        GameSession.setPlayer(new Player("Sir Alistair")); // Player Objekt erstellen
        ViewManager.switchTo("game-view.fxml"); // View wechseln
    }

    @FXML
    private void onKnightGirlSelected() {
        System.out.println("Knight Girl selected!");

        GameSession.setSelectedCharacterType("knight_girl"); // Typ als "knight_girl" speichern
        GameSession.setPlayer(new Player("Lady Lala")); // Player erstellen
        ViewManager.switchTo("game-view.fxml"); // View wechseln
    }
}
