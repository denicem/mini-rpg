package com.minirpg.game;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController {
    @FXML
    private ImageView gameSceneView;

    @FXML
    private Label storyText;
    @FXML
    private Button choiceButton;
    @FXML
    private Button exitButton;

    private int storyState = 0;

    @FXML
    public void initialize() {
        this.setGameSceneView("castle.png");
        this.storyState = 0; // Start at the beginning;
        storyText.setText("You stand at the entrance of a big castle.");
        this.choiceButton.setText("Go inside");
    }

    @FXML
    protected void onChoiceButtonClick() {
        // This is a simple "state machine"
        // It checks the current state and moves to the next one.
        switch(this.storyState) {
            case 0: // The first state
                this.exitButton.setVisible(false);
                this.setGameSceneView("castle-inside.png");
                this.storyText.setText("A giant bat swoops down! What do you do?");
                this.choiceButton.setText("Fight!");
                storyState = 1; // Move to the next state
                break ;
            case 1: // The second state
                this.storyText.setText("You bravely fight the bat and win! You find a small chest.");
                this.choiceButton.setText("Open it");
                storyState = 2; // Move to the next state
                break ;
            case 2: // The third state
                this.storyText.setText("You found 10 Gold! Your adventure begins.");
                this.choiceButton.setText("Restart");
                this.exitButton.setVisible(true);
                this.storyState = 3; // Move to the "end" state
                break ;
            case 3: // The "end" state
            default: // A safety net
                // Go back to the beginning
                initialize();
                break ;
        }
    }

    @FXML protected void onExitButtonClick() {
        this.setGameSceneView("forest.png");
        this.choiceButton.setVisible(false);
        this.exitButton.setVisible(false);
        this.storyText.setText("Coward!");

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            exitAndCloseWindow();
        });

        pause.play();
    }

    private void setGameSceneView(String imageName) {
        String imagePath = "images/" + imageName;
        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            this.gameSceneView.setImage(image);
        } catch (Exception e) {
            System.err.println("Error loading image: " + imagePath);
            exitAndCloseWindow();
        }
    }

    private void exitAndCloseWindow() {
        Stage stage = (Stage) this.storyText.getScene().getWindow();
        stage.close();
    }
}
