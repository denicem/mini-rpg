package com.minirpg.game.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.Objects;

public class Helper {
    private static final boolean IS_DEBUG_MODE = true;

    public static void logCall(Object caller) {
        if (IS_DEBUG_MODE) {
            System.out.printf("[%s]: initialized and assets loading...%n", caller.getClass().getSimpleName());
        }
    }

    public static void loadImage(ImageView img, String imgPath) {
        if (img == null || imgPath == null) return;
        try {
            img.setImage(new Image(Objects.requireNonNull(Helper.class.getResourceAsStream(imgPath))));
        } catch (Exception e) {
            System.err.println("[ERROR] on loading image: " + imgPath);
        }
    }

    /**
     * Delays the execution of a specific action without freezing the game UI.
     * @param millis       The wait time in milliseconds.
     * @param continuation The code/action to run once the time has passed.
     */
    public static void delay(int millis, Runnable continuation) {
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.millis(millis));
        pause.setOnFinished(event -> continuation.run());
        pause.play();
    }

    public static Timeline animateText(Label label, String fullText) {
        if (label == null || fullText == null) return null;

        label.setText("");
        final StringBuilder sb = new StringBuilder();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(70), e -> {
                    if (sb.length() < fullText.length()) {
                        sb.append(fullText.charAt(sb.length()));
                        label.setText(sb.toString());
                    }
                })
        );

        timeline.setCycleCount(fullText.length());
        timeline.play();
        return timeline;
    }

}
