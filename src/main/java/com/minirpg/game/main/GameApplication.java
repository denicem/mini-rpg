package com.minirpg.game.main;

import com.minirpg.game.util.ViewManager;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        String fontPath = "/com/minirpg/game/view/font/PressStart2P-Regular.ttf";
        Font customFont = Font.loadFont(getClass().getResourceAsStream(fontPath), 20);

        if (customFont == null) {
            System.err.println("Font could not be found! Check path: " + fontPath);
        } else {
            System.out.println("Font loaded: " + customFont);
        }

        // 1. Dem ViewManager die Haupt-Stage übergeben, damit er sie steuern kann
        ViewManager.setStage(stage);

        // 2. Den ersten Wechsel zum Hauptmenü über den Manager auslösen
        ViewManager.switchTo("main-menu-view.fxml");

        // Optionale Einstellungen für das Fenster
        stage.setTitle("The Knight Trials.");
        stage.setResizable(false);
        // stage.show() wird bereits im ViewManager.switchTo() aufgerufen
    }
}
