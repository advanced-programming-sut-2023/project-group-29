package view.shape;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.map.Map;
import view.menus.MapMenu;

public class CircleImage extends Circle {
    public CircleImage(String imageAddress, int radius) {
        super();
        Image image = new Image(MapMenu.class.getResource(imageAddress).toString());
        this.setFill(new ImagePattern(image));
        this.setRadius(radius);
    }
}
