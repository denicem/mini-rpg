package com.minirpg.game.controller;

import com.minirpg.game.model.Player;
import com.minirpg.game.model.StoryManager;
import com.minirpg.game.util.Assets;
import com.minirpg.game.util.GameData;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController {
    @FXML
    private ImageView gameSceneView;
    @FXML
    private ImageView playerImageView;

    @FXML
    private Label storyText;
    @FXML
    private Button choiceButton;
    @FXML
    private Button exitButton;

    private int storyState = 0;
    private StoryManager sm;
    private Player player;

    private String[] chunks = new String[0];
    private int chunkIndex = 0;
    private boolean isShowingChunks = false;

    private String[] chunks = new String[0];
    private int chunkIndex = 0;
    private boolean isShowingChunks = false;

    @FXML
    public void initialize() {
        this.sm = new StoryManager();

        //Characterselection
        //1. Spieler aus GameData laden
        this.player = GameData.getPlayer();

        //2. Charakterbild setzen basierend auf der Auswahl in der CharacterSelection
        if (GameData.getSelectedCharacterType() != null) {
            String charType = GameData.getSelectedCharacterType();
            String imagePath = "/com/minirpg/game/characters/" + charType + ".png";

            try {
                playerImageView.setImage(new Image(getClass().getResourceAsStream(imagePath)));
            } catch (Exception e) {
                System.err.println("Character could not load.");
            }
        }

        this.storyState = 0;
        this.setGameSceneView(Assets.BG_CASTLE_INFRONT);
        this.storyState = 0; // Start at the beginning;
        storyText.setText("You stand at the entrance of a big castle.");
        this.choiceButton.setText("Go inside");
        this.setGameSceneView(Assets.BG_CASTLE_INFRONT);
//        startChunks(sm.getStoryChunks(StoryManager.ACT_1));
//        choiceButton.setText("Weiter");
//        exitButton.setVisible(true);

    }

    @FXML
    protected void onChoiceButtonClick() {
        // If we are currently showing chunked text, advance text first
        if (advanceChunk()) {
            return;
        }
        // This is a simple "state machine"
        // It checks the current state and moves to the next one.
        switch(this.storyState) {
            case 0: // The first state
                this.exitButton.setVisible(false);
                this.setGameSceneView(Assets.BG_FOREST);
                startChunks(sm.getStoryChunks(StoryManager.ACT_1));
                this.choiceButton.setText("Weiter!");
                storyState = 1; // Move to the next state
                break ;
            case 1: // The second state
                startChunks(sm.getStoryChunks(StoryManager.ACT_2));
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
        this.setGameSceneView(Assets.BG_FOREST);
        this.choiceButton.setVisible(false);
        this.exitButton.setVisible(false);
        this.storyText.setText("Coward!");

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            exitAndCloseWindow();
        });

        pause.play();
    }

    private void setGameSceneView(String imagePath) {
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

    @FXML private VBox gamePane;
    @FXML private StackPane endPane;

    @FXML private ImageView endBackgroundView;
    @FXML private ImageView endKnightView;

    @FXML private Label endTitleLabel;
    @FXML private Label endSubtitleLabel;

    @FXML private Button restartButton;
    @FXML private Button quitButton;

    private void showEndScreen(boolean win) {
        gamePane.setVisible(false);
        endPane.setVisible(true);

        endBackgroundView.setImage(new Image(getClass().getResourceAsStream(Assets.BG_ENDING)));

        if (win) {
            endTitleLabel.setText("YOU DID IT" );
            endSubtitleLabel.setText("CONGRATULATIONS, YOU'VE SLAIN THE DRAGON" );
            endKnightView.setImage(new Image(getClass().getResourceAsStream(Assets.CH_KNIGHT_WITH_SWORD_AND_SHIELD)));
        } else {
            endTitleLabel.setText("YOU DIED");
            endSubtitleLabel.setText("THE DRAGON WINS. YOU'VE BEEN TURNED INTO A SNACK.");
            endKnightView.setImage(new Image(getClass().getResourceAsStream(Assets.CH_KNIGHT_SLAIN)));
        }
    }

    @FXML protected void onRestartButtonClick() {
        endPane.setVisible(false);
        gamePane.setVisible(true);
        initialize();
    }

    @FXML protected void onQuitButtonClick() {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

    private void startChunks(String[] newChunks) {
        if (newChunks == null || newChunks.length == 0) newChunks = new String[]{""};

        this.chunks = newChunks;
        this.chunkIndex = 0;
        this.isShowingChunks = newChunks.length > 1;

        storyText.setText(chunks[0]);
        }

    private boolean advanceChunk() {
        if (!isShowingChunks) return false;

        if (chunkIndex < chunks.length - 1) {
            chunkIndex++;
            storyText.setText(chunks[chunkIndex]);
            return true;
        } else {
            isShowingChunks = false;
            return false;
        }
    }
}
