package view.menus.gamepopupmenus;

import controller.menucontrollers.GameController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Asset;
import model.GameData;
import model.ImagePracticalFunctions;
import model.people.humanClasses.Soldier;
import model.people.humanTypes.SoldierType;
import model.weapons.weaponClasses.Equipments;
import model.weapons.weaponClasses.OffensiveWeapons;
import model.weapons.weaponClasses.StaticOffensiveWeapons;
import model.weapons.weaponTypes.EquipmentsType;
import model.weapons.weaponTypes.OffensiveWeaponsType;
import model.weapons.weaponTypes.StaticOffensiveWeaponsType;
import model.weapons.weaponTypes.TrapType;
import view.menus.EnterMenu;

import java.util.HashMap;

public class AddOrRemoveSelectedUnits {
    private final int numberOfPicturesInRow = 9;
    private final HashMap<String, Label> labelHashMap = new HashMap<>();
    @FXML
    private Pane addRemovePane;

    public void exitPopUp(MouseEvent mouseEvent) {
        GameController.getGameData().getGameGraphicFunctions().exitPopUp();
    }

    @FXML
    public void initialize() {
        int index = 0;
        for (SoldierType type : SoldierType.values()) {
            Image image = new Image(EnterMenu.class.getResource(type.getHumanType().showingImageFilePath()).toExternalForm());
            setUpUnitImage(image, index, type.getName());
            index++;
        }

        for (EquipmentsType type : EquipmentsType.values()) {
            Image image = new Image(EnterMenu.class.getResource(type.getWeaponTypes().showingImageFilePath()).toExternalForm());
            setUpUnitImage(image, index, type.getName());
            index++;
        }
        for (OffensiveWeaponsType type : OffensiveWeaponsType.values()) {
            Image image = new Image(EnterMenu.class.getResource(type.getWeaponTypes().showingImageFilePath()).toExternalForm());
            setUpUnitImage(image, index, type.getName());
            index++;
        }
        for (TrapType type : TrapType.values()) {
            Image image = new Image(EnterMenu.class.getResource(type.getWeaponTypes().showingImageFilePath()).toExternalForm());
            setUpUnitImage(image, index, type.getName());
            index++;
        }
        for (StaticOffensiveWeaponsType type : StaticOffensiveWeaponsType.values()) {
            Image image = new Image(EnterMenu.class.getResource(type.getWeaponTypes().showingImageFilePath()).toExternalForm());
            setUpUnitImage(image, index, type.getName());
            index++;
        }
    }

    public void setUpUnitImage(Image image, int index, String typeName) {
        ImageView imageView = new ImageView(image);
        ImagePracticalFunctions.fitWidthHeight(imageView, 70, 70);
        imageView.setLayoutX(75 * (index % numberOfPicturesInRow) + 50);
        imageView.setLayoutY(120 * (index / numberOfPicturesInRow) + 80);

        GameData gameData = GameController.getGameData();

        Label label = new Label("" + gameData.getCountOfUnitTypeOnArrayList(gameData.getSelectedUnits(), typeName));
        label.setLayoutX(75 * (index % numberOfPicturesInRow) + 50);
        label.setLayoutY(120 * (index / numberOfPicturesInRow) + 170);
        labelHashMap.put(typeName, label);

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int countOfSelected = gameData.getCountOfUnitTypeOnArrayList(gameData.getSelectedUnits(), typeName);
                int countOfAll = gameData.getCountOfUnitTypeOnArrayList(gameData.getAllUnitsInSelectedCells(), typeName);

                countOfSelected--;
                if (countOfSelected == -1) {
                    countOfSelected = countOfAll;
                    addAllUnits(typeName);
                }
                else {
                    removeOneUnit(typeName);
                }
                labelHashMap.get(typeName).setText("" + countOfSelected);
            }
        });

        addRemovePane.getChildren().add(imageView);
        addRemovePane.getChildren().add(label);
    }

    private void addAllUnits(String typeName) {
        GameData gameData = GameController.getGameData();

        for (Asset asset : gameData.getAllUnitsInSelectedCells()) {
            if (asset instanceof Soldier soldier && soldier.getSoldierType().getName().equals(typeName))
                gameData.getSelectedUnits().add(asset);
            else if (asset instanceof OffensiveWeapons offensiveWeapons && offensiveWeapons.getOffensiveWeaponsType().getName().equals(typeName))
                gameData.getSelectedUnits().add(asset);
            else if (asset instanceof StaticOffensiveWeapons staticOffensiveWeapons && staticOffensiveWeapons.getStaticOffensiveWeaponsType().getName().equals(typeName))
                gameData.getSelectedUnits().add(asset);
            else if (asset instanceof Equipments equipments && equipments.getEquipmentsType().getName().equals(typeName))
                gameData.getSelectedUnits().add(asset);
        }
    }

    private void removeOneUnit(String typeName) {
        GameData gameData = GameController.getGameData();

        for (Asset asset : gameData.getAllUnitsInSelectedCells()) {
            if (!gameData.getSelectedUnits().contains(asset))
                continue;

            if (asset instanceof Soldier soldier && soldier.getSoldierType().getName().equals(typeName)) {
                gameData.getSelectedUnits().remove(asset);
                return;
            }
            else if (asset instanceof OffensiveWeapons offensiveWeapons && offensiveWeapons.getOffensiveWeaponsType().getName().equals(typeName)) {
                gameData.getSelectedUnits().remove(asset);
                return;
            }
            else if (asset instanceof StaticOffensiveWeapons staticOffensiveWeapons && staticOffensiveWeapons.getStaticOffensiveWeaponsType().getName().equals(typeName)) {
                gameData.getSelectedUnits().remove(asset);
                return;
            }
            else if (asset instanceof Equipments equipments && equipments.getEquipmentsType().getName().equals(typeName)) {
                gameData.getSelectedUnits().remove(asset);
                return;
            }
        }
    }
}
