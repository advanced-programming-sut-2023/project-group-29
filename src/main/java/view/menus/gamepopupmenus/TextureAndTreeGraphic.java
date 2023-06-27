package view.menus.gamepopupmenus;

import controller.menucontrollers.GameController;
import controller.menucontrollers.MapFunctions;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.GameData;
import model.ImagePracticalFunctions;
import model.map.CellType;
import model.map.TreeType;
import view.menus.GameGraphicFunctions;
import view.menus.MapMenu;
import view.messages.MapMenuMessages;

public class TextureAndTreeGraphic {
    @FXML
    private Pane textureAndTreePane;

    private final int numberOfPicturesInRow = 9;

    public void exitPopUp() {
        GameController.getGameData().getGameGraphicFunctions().exitPopUp();
    }

    @FXML
    public void initialize() {
        int index = 0;
        for (TreeType type : TreeType.values()) {
            Image image = new Image(MapMenu.class.getResource(type.getShowingImagePath()).toString());
            setupImage(image, index, type.getName(), TreeType.class);
            index++;
        }
        for (CellType type : CellType.values()) {
            Image image = type.getImage();
            setupImage(image, index, type.getName(), CellType.class);
            index++;
        }
    }

    public void setupImage(Image image, int index, String typeName, Class myClass) {
        ImageView imageView = new ImageView(image);
        ImagePracticalFunctions.fitWidthHeight(imageView, 70, 70);
        imageView.setLayoutX(75 * (index % numberOfPicturesInRow) + 50);
        imageView.setLayoutY(80 * (index / numberOfPicturesInRow) + 120);
        if (myClass == TreeType.class) {
            imageView.setOnMouseClicked(mouseEvent -> clickOnTreeFunction(typeName));
        } else if (myClass == CellType.class) {
            imageView.setOnMouseClicked(mouseEvent -> clickOnCellTypeFunction(typeName));
        }
        textureAndTreePane.getChildren().add(imageView);
    }

    private void clickOnCellTypeFunction(String typeName) {
        GameData gameData=GameController.getGameData();
        int x = gameData.getStartSelectedCellsPosition().first + gameData.getCornerCellIndex().first;
        int y = gameData.getStartSelectedCellsPosition().second + gameData.getCornerCellIndex().second;

        CellType cellType = CellType.getCellTypeByName(typeName);
        String result = MapFunctions.setBlockTexture(cellType, x, y);
        GameGraphicFunctions gameGraphicFunctions = GameController.getGameData().getGameGraphicFunctions();
        switch (result) {
            case "You can't change texture of this cell!" -> gameGraphicFunctions.alertMessage
                    (Color.RED, "unchangeable", result);
            case "Type of the cell was successfully changed" -> gameGraphicFunctions.alertMessage
                    (Color.GREEN, "success", result);
        }

    }

    private void clickOnTreeFunction(String treeName) {
        GameData gameData=GameController.getGameData();
        int x = gameData.getStartSelectedCellsPosition().first + gameData.getCornerCellIndex().first;
        int y = gameData.getStartSelectedCellsPosition().second + gameData.getCornerCellIndex().second;

        MapMenuMessages result = MapFunctions.dropTree(x, y, treeName);
        if (result == MapMenuMessages.SUCCESSFUL) {
            GameGraphicFunctions gameGraphicFunctions = GameController.getGameData().getGameGraphicFunctions();
            gameGraphicFunctions.alertMessage(Color.GREEN, "success", "The tree was successfully dropped");
        }
    }
}

