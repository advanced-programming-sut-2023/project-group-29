package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.Command;

public class Avatar extends Rectangle {
    public Avatar() {
        super(490, 0, 100, 100);
        this.setFill(new ImagePattern(new Image(AppData.getCurrentUser().getAvatar())));
    }


}
