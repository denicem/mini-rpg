package com.minirpg.game.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
}
