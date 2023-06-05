package view.shape;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import view.menus.GameMenuGraphic;

public class CircleImage extends Circle {
    public CircleImage(String imageAddress, int radius) {
        super();
        Image image = new Image(GameMenuGraphic.class.getResource(imageAddress).toString());
        this.setFill(new ImagePattern(image));
        this.setRadius(radius);
    }
}
