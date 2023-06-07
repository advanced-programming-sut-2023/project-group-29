package view.shape;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.menus.GameMenuGraphic;

public class BuildingIcon extends Rectangle {
    public BuildingIcon(int width, String address) {
        this.setWidth(width);
        this.setHeight(width);
        Image image = new Image(GameMenuGraphic.class.getResource(address).toString());
        this.setFill(new ImagePattern(image));
    }
}
