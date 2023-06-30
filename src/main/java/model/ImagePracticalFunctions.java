package model;

import javafx.scene.image.ImageView;

public class ImagePracticalFunctions {
    public static void fitWidthHeight(ImageView imageView, int width, int height) {
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
    }
}
