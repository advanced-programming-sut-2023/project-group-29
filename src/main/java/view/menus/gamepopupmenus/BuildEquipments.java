package view.menus.gamepopupmenus;

import controller.menucontrollers.GameController;
import controller.menucontrollers.UnitFunctions;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.ImagePracticalFunctions;
import model.weapons.weaponTypes.EquipmentsType;
import model.weapons.weaponTypes.OffensiveWeaponsType;
import model.weapons.weaponTypes.StaticOffensiveWeaponsType;
import model.weapons.weaponTypes.TrapType;
import view.menus.EnterMenu;

public class BuildEquipments {
    @FXML
    private HBox equipmentHBox;

    public void exitPopUp(MouseEvent mouseEvent) {
        GameController.getGameData().getGameGraphicFunctions().exitPopUp();
    }

    @FXML
    public void initialize() {
        int index = 0;
        for (EquipmentsType type : EquipmentsType.values()) {
            Image image = new Image(EnterMenu.class.getResource(type.getWeaponTypes().showingImageFilePath()).toExternalForm());
            setupImage(image, index, type.getName());
            index++;
        }
        for (OffensiveWeaponsType type : OffensiveWeaponsType.values()) {
            Image image = new Image(EnterMenu.class.getResource(type.getWeaponTypes().showingImageFilePath()).toExternalForm());
            setupImage(image, index, type.getName());
            index++;
        }
        for (TrapType type : TrapType.values()) {
            Image image = new Image(EnterMenu.class.getResource(type.getWeaponTypes().showingImageFilePath()).toExternalForm());
            setupImage(image, index, type.getName());
            index++;
        }
        for (StaticOffensiveWeaponsType type : StaticOffensiveWeaponsType.values()) {
            Image image = new Image(EnterMenu.class.getResource(type.getWeaponTypes().showingImageFilePath()).toExternalForm());
            setupImage(image, index, type.getName());
            index++;
        }
    }

    public void setupImage(Image image, int index, String typeName) {
        ImageView imageView = new ImageView(image);
        ImagePracticalFunctions.fitWidthHeight(imageView, 70, 70);
        imageView.setLayoutX(50 * index + 5);

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UnitFunctions.buildEquipment(typeName);
                GameController.getGameData().getMapMenu().setUpAndShowMap();
            }
        });

        equipmentHBox.getChildren().add(imageView);
    }
}
