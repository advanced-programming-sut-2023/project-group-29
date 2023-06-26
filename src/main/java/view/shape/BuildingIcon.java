package view.shape;

import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.buildings.buildingTypes.BuildType;
import view.menus.MapMenu;

public class BuildingIcon extends Rectangle {
    private static String draggingBuildingName = null;
    private Image image;
    public BuildingIcon(int width, String address) {
        this.setWidth(width);
        this.setHeight(width);
        Image image = new Image(MapMenu.class.getResource(address).toString());
        this.setFill(new ImagePattern(image));
        this.image = image;
    }

    public static String getDraggingBuildingName() {
        return draggingBuildingName;
    }

    public Image getImage() {
        return image;
    }


    public void setDragFunctions(String buildingName) {
        this.setOnDragDetected(mouseEvent -> {
            draggingBuildingName = buildingName;
            Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(image);
            dragboard.setContent(content);
            mouseEvent.consume();
        });
    }
}
