package view.menus.gamepopupmenus;

import controller.menucontrollers.BuildingFunctions;
import controller.menucontrollers.GameController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.ImagePracticalFunctions;
import model.buildings.buildingClasses.UnitCreator;
import model.people.humanTypes.SoldierType;
import view.menus.GameGraphicFunctions;
import view.menus.MapMenu;
import view.messages.SelectBuildingMenuMessages;

public class CreateUnitGraphic {
    @FXML
    private Pane createUnitPane;

    private final int numberOfPicturesInRow = 9;

    public void exitPopUp() {
        GameController.getGameData().getGameGraphicFunctions().exitPopUp();
    }

    @FXML
    public void initialize() {
        UnitCreator unitCreator = (UnitCreator) BuildingFunctions.getSelectedBuilding();
        int index = 0;
        for (SoldierType type : SoldierType.values()) {
            if (!BuildingFunctions.buildingAndTroopMatch(unitCreator, type)) continue;
            Image image = new Image(MapMenu.class.getResource(type.getHumanType().showingImageFilePath()).toString());
            setupUnitImage(image, index, type.getName());
            index++;
        }
    }

    public void setupUnitImage(Image image, int index, String typeName) {
        ImageView imageView = new ImageView(image);
        ImagePracticalFunctions.fitWidthHeight(imageView, 70, 70);
        imageView.setLayoutX(75 * (index % numberOfPicturesInRow) + 50);
        imageView.setLayoutY(80 * (index / numberOfPicturesInRow) + 120);
        imageView.setOnMouseClicked(mouseEvent -> {
            SelectBuildingMenuMessages result = BuildingFunctions.createUnit(typeName, 1);
            GameGraphicFunctions gameGraphicFunctions = GameController.getGameData().getGameGraphicFunctions();
            switch (result) {
                case LACK_OF_COINS -> gameGraphicFunctions.alertMessage
                        (Color.RED, "lack of coin", "You don't have enough coin to create this unit!");
                case LACK_OF_WEAPON -> gameGraphicFunctions.alertMessage
                        (Color.RED, "lack of weapon", "You don't have proper weapon to equip this unit!");
                case LACK_OF_HUMAN -> gameGraphicFunctions.alertMessage
                        (Color.RED, "lack of human", "There is no person to become your unit!");
                case SUCCESS -> gameGraphicFunctions.alertMessage
                        (Color.GREEN, "success", "Your unit was successfully created!");
            }
        });
        createUnitPane.getChildren().add(imageView);
    }
}
