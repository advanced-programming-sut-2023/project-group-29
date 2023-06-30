package view.menus.gamepopupmenus;

import controller.menucontrollers.GameController;
import controller.menucontrollers.MapFunctions;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.GameData;
import model.ImagePracticalFunctions;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.buildingTypes.BuildType;
import view.menus.GameGraphicFunctions;
import view.menus.MapMenu;
import view.messages.MapMenuMessages;

public class DropBuildingGraphics {
    private final int numberOfPicturesInRow = 10;
    @FXML
    private TextField ownerNumber;
    @FXML
    private Pane dropBuildingPane;

    private static void handleResult(GameGraphicFunctions gameGraphicFunctions, MapMenuMessages result) {
        switch (result) {
            case FULL_CELL -> gameGraphicFunctions.alertMessage
                    (Color.RED, "full cell", "Another building has been already built here");
            case IMPROPER_CELL_TYPE -> gameGraphicFunctions.alertMessage
                    (Color.RED, "improper cell type", "This cell is not good for building this building!");
            case TWO_MAIN_KEEP -> gameGraphicFunctions.alertMessage
                    (Color.RED, "two main keep", "having two main keeps is illegal");
            case UNCONNECTED_STOREROOMS -> gameGraphicFunctions.alertMessage
                    (Color.RED, "unconnected storerooms", "Your storerooms should be connected to each other");
            case SUCCESSFUL -> gameGraphicFunctions.alertMessage
                    (Color.GREEN, "success", "The building was successfully droped");
        }
    }

    public void exitPopUp() {
        GameController.getGameData().getGameGraphicFunctions().exitPopUp();
    }

    @FXML
    public void initialize() {
        int index = 0;
        for (BuildType type : Building.getBuildingTypesAndTheirGroup().keySet()) {
            BuildingType buildingType = type.getBuildingType();
            String imageAddress = "/images/buildings/" + buildingType.category().name() + "/" +
                    buildingType.name() + ".png";
            Image image = new Image(MapMenu.class.getResource(imageAddress).toString());
            setupBuildingImage(image, index, buildingType.name());
            index++;
        }
    }

    public void setupBuildingImage(Image image, int index, String buildingName) {
        ImageView imageView = new ImageView(image);
        ImagePracticalFunctions.fitWidthHeight(imageView, 60, 60);
        imageView.setLayoutX(65 * (index % numberOfPicturesInRow) + 50);
        imageView.setLayoutY(70 * (index / numberOfPicturesInRow) + 120);
        imageView.setOnMouseClicked(mouseEvent -> functionOnBuildingClick(buildingName));
        dropBuildingPane.getChildren().add(imageView);
    }

    private void functionOnBuildingClick(String buildingName) {
        GameData gameData = GameController.getGameData();
        GameGraphicFunctions gameGraphicFunctions = gameData.getGameGraphicFunctions();
        int x = gameData.getStartSelectedCellsPosition().first + gameData.getCornerCellIndex().first;
        int y = gameData.getStartSelectedCellsPosition().second + gameData.getCornerCellIndex().second;
        int playerNumber = 0;
        if (!ownerNumber.getText().matches("\\d")) {
            gameGraphicFunctions.alertMessage(Color.RED, "drop failed", "please enter a valid player number");
            return;
        }
        else playerNumber = Integer.parseInt(ownerNumber.getText());
        if (playerNumber <= 0 || playerNumber > gameData.getNumberOfPlayers()) {
            gameGraphicFunctions.alertMessage(Color.RED, "drop failed", "please enter a valid player number");
            return;
        }
        MapMenuMessages result = MapFunctions.dropBuildingAsAdmin(x, y, buildingName, playerNumber);
        handleResult(gameGraphicFunctions, result);
    }
}
