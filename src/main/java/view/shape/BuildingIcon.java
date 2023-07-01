package view.shape;

import controller.menucontrollers.GameController;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.menus.MapMenu;

public class BuildingIcon extends Rectangle {
    private static String draggingBuildingName = null;
    private final Image image;

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

            if(GameController.getGameData().getPlayerOfTurn()!=GameController.getGameData().getCurrentUserPlayerNumber()) {
                GameController.getGameData().getGameGraphicFunctions().alertMessage(Color.RED, "", "its not your turn");
                return;
            }

            draggingBuildingName = buildingName;
            Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(image);
            dragboard.setContent(content);
            mouseEvent.consume();
        });
    }
}
