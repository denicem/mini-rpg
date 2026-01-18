package com.minirpg.game.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Helper {
    public static void loadImage(ImageView img, String imgPath) {
        if (img == null || imgPath == null) return;
        try {
            img.setImage(new Image(Objects.requireNonNull(Helper.class.getResourceAsStream(imgPath))));
        } catch (Exception e) {
            System.err.println("Error on loading image: " + imgPath);
        }
    }
}
