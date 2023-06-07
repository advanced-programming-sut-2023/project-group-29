package view.shape;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.buildings.buildingTypes.BuildType;
import view.menus.GameMenuGraphic;

public class BuildingIcon extends Rectangle {
    private Image image;
    public BuildingIcon(int width, String address) {
        this.setWidth(width);
        this.setHeight(width);
        Image image = new Image(GameMenuGraphic.class.getResource(address).toString());
        this.setFill(new ImagePattern(image));
        this.image = image;
    }

    public Image getImage() {
        return image;
    }


    public void setDragFunctions(BuildType buildType) {
        this.setOnDragDetected(mouseEvent -> {
            Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(image);
            dragboard.setContent(content);
            mouseEvent.consume();
        });
        this.setOnDragDone(dragEvent -> {
            if (dragEvent.getTransferMode() == TransferMode.MOVE){
                //todo jasbi build building(buildType)
                System.out.println("built!");
            }
            dragEvent.consume();
        });
    }
}
