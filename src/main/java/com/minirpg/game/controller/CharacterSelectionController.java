package com.minirpg.game.controller;

import com.minirpg.game.model.Player;
import com.minirpg.game.util.Assets;
import com.minirpg.game.util.GameData;
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

        //Typ als "knight" speichern
        GameData.setSelectedCharacterType("knight");
        //Player Objekt erstellen
        GameData.setPlayer(new Player("Sir Alistair"));
        //View wechseln
        ViewManager.switchTo("game-view.fxml");
    }

    @FXML
    private void onKnightGirlSelected() {
        System.out.println("Knight selected!");

        //Typ als "knight_girl" speichern
        GameData.setSelectedCharacterType("knight_girl");
        //Player erstellen
        GameData.setPlayer(new Player("Lady Lala"));
        //View wechseln
        ViewManager.switchTo("game-view.fxml");
    }
}
