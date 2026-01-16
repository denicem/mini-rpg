package com.minirpg.game.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ViewManager {
    private static Stage primaryStage;

    // Diese Methode wird einmalig beim Spielstart aufgerufen
    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    // Die Hauptmethode zum Wechseln der Ansicht
    public static void switchTo(String fxmlFileName) {
        try {
            // Der Pfad ist relativ zu deiner Ressourcen-Struktur
            String path = "/com/minirpg/game/view/" + fxmlFileName;

            Parent root = FXMLLoader.load(ViewManager.class.getResource(path));

            // Wenn noch keine Szene da ist, erstelle eine
            if (primaryStage.getScene() == null) {
                primaryStage.setScene(new Scene(root, 1280, 720));
            } else {
                // Ansonsten tausche nur den Inhalt aus
                primaryStage.getScene().setRoot(root);
            }

            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Konnte View nicht laden: " + fxmlFileName);
            e.printStackTrace();
        }
    }
}
