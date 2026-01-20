package com.minirpg.game.util;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ViewManager {
    private static Stage primaryStage;

    // Diese Methode wird einmalig beim Spielstart aufgerufen
    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    // Die Hauptmethode zum Wechseln der Ansicht
    public static void switchTo(String fxmlFile) {
        if (primaryStage.getScene() != null && primaryStage.getScene().getRoot() != null) {

            // 1. Fade-Out der aktuellen Szene
            Parent currentRoot = primaryStage.getScene().getRoot();
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), currentRoot);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            // Sobald das Ausblenden fertig ist, wird die neue Szene geladen
            fadeOut.setOnFinished(e -> loadNewScene(fxmlFile));
            fadeOut.play();
        } else {
            // Falls noch keine Szene da ist (beim Start), direkt laden
            loadNewScene(fxmlFile);
        }
    }

    private static void loadNewScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource("/com/minirpg/game/view/" + fxmlFile));
            Parent root = loader.load();

            //Neue Szene mit Hintergrund schwarz
            Scene scene = new Scene(root);
            scene.setFill(Color.BLACK);

            root.setOpacity(0);

            // 2. Fade-In der neuen Szene vorbereitet
            primaryStage.setScene(scene);
            primaryStage.show();

            // Fade-In Animation starten
            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), root);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
