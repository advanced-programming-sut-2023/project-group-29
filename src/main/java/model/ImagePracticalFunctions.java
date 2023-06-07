package model;

import javafx.scene.image.ImageView;

public class ImagePracticalFunctions {
    public static void fitWidthHeight(ImageView imageView,int width,int height){
        imageView.maxHeight(height);
        imageView.minHeight(height);
        imageView.maxWidth(width);
        imageView.minWidth(width);
    }
}
