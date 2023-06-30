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

public class TextureAndTreeGraphic {
    private final int numberOfPicturesInRow = 9;
    @FXML
    private Pane textureAndTreePane;

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
        GameData gameData = GameController.getGameData();
        int x1 = gameData.getStartSelectedCellsPosition().first + gameData.getCornerCellIndex().first;
        int y1 = gameData.getStartSelectedCellsPosition().second + gameData.getCornerCellIndex().second;
        int x2 = gameData.getEndSelectedCellsPosition().first + gameData.getCornerCellIndex().first;
        int y2 = gameData.getEndSelectedCellsPosition().second + gameData.getCornerCellIndex().second;
        if (myClass == TreeType.class) {
            imageView.setOnMouseClicked(mouseEvent -> {
                clickOnTreeFunction(typeName, x1, y1, x2, y2);
            });
        }
        else if (myClass == CellType.class) {
            imageView.setOnMouseClicked(mouseEvent -> {
                clickOnCellTypeFunction(typeName, x1, y1, x2, y2);
            });
        }
        textureAndTreePane.getChildren().add(imageView);
    }

    private void clickOnCellTypeFunction(String typeName, int x1, int y1, int x2, int y2) {
        CellType cellType = CellType.getCellTypeByName(typeName);
        String result, finalResult = "Type of the cell was successfully changed";
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                result = MapFunctions.setBlockTexture(cellType, i, j);
                if (result.equals("You can't change texture of this cell!")) {
                    finalResult = "You can't change texture of this cell!";
                }
            }
        }
        GameGraphicFunctions gameGraphicFunctions = GameController.getGameData().getGameGraphicFunctions();
        switch (finalResult) {
            case "You can't change texture of this cell!" -> gameGraphicFunctions.alertMessage
                    (Color.RED, "unchangeable", finalResult);
            case "Type of the cell was successfully changed" -> gameGraphicFunctions.alertMessage
                    (Color.GREEN, "success", finalResult);
        }

    }

    private void clickOnTreeFunction(String treeName, int x1, int y1, int x2, int y2) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                MapFunctions.dropTree(i, j, treeName);
            }
        }
        GameGraphicFunctions gameGraphicFunctions = GameController.getGameData().getGameGraphicFunctions();
        gameGraphicFunctions.alertMessage(Color.GREEN, "success", "The tree was successfully dropped");
    }
}

