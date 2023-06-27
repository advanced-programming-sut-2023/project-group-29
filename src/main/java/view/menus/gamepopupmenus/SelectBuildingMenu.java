package view.menus.gamepopupmenus;

import controller.MenuNames;
import controller.menucontrollers.BuildingFunctions;
import controller.menucontrollers.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import model.buildings.Building;
import model.buildings.buildingClasses.OtherBuildings;
import model.buildings.buildingTypes.OtherBuildingsType;
import view.menus.GameGraphicFunctions;
import view.messages.SelectBuildingMenuMessages;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectBuildingMenu {
    @FXML
    private Button changeBridgeStateButton;
    @FXML
    private Button copyButton;
    @FXML
    private Button createUnitButton;
    @FXML
    private Button repairButton;

    public void exitPopUp() {
        GameController.getGameData().getGameGraphicFunctions().exitPopUp();
    }

    @FXML
    public void initialize() {
        Building building = BuildingFunctions.getSelectedBuilding();
        String name = building.getName();
        setVisibility(building, name);
        prepareRepairButton();
        prepareCreateUnitButton();
        prepareCopyButton();
        prepareChangeBridgeStateButton();
    }

    private void prepareChangeBridgeStateButton() {
        changeBridgeStateButton.setOnMouseClicked(mouseEvent -> {
            SelectBuildingMenuMessages result = BuildingFunctions.changeBridgeState();
            GameGraphicFunctions gameGraphicFunctions = GameController.getGameData().getGameGraphicFunctions();
            if (result == SelectBuildingMenuMessages.SUCCESS) {
                gameGraphicFunctions.alertMessage
                        (Color.GREEN, "success", "The state of bridge was successfully changed");
            }
        });
    }

    private void prepareCopyButton() {
        copyButton.setOnMouseClicked(mouseEvent -> {
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection stringSelection = new StringSelection(BuildingFunctions.getSelectedBuilding().getName());
            c.setContents(stringSelection, stringSelection);
        });
    }

    private void prepareCreateUnitButton() {
        createUnitButton.setOnMouseClicked(mouseEvent -> {
            exitPopUp();
            try {
                GameController.getGameData().getGameGraphicFunctions().openCreateUnitMenu();
            } catch (IOException e) {
            }
        });
    }

    private void prepareRepairButton() {
        repairButton.setOnMouseClicked(mouseEvent -> {
            SelectBuildingMenuMessages result = BuildingFunctions.repairBuilding();
            GameGraphicFunctions gameGraphicFunctions = GameController.getGameData().getGameGraphicFunctions();
            switch (result) {
                case LACK_OF_STONE -> {
                    gameGraphicFunctions.alertMessage(Color.RED,"No stone", "You don't have enough stone!");
                }
                case ENEMY_IS_NEAR -> {
                    gameGraphicFunctions.alertMessage
                            (Color.RED,"Enemy", "You can't repair while enemy is near!");
                }
                case SUCCESS -> {
                    gameGraphicFunctions.alertMessage
                            (Color.GREEN,"success", "Building was successfully repaired");
                }
            }
        });
    }

    private void setVisibility(Building building, String name) {

        if (Building.getGroupNumberByBuildingName(name) != 9) {
            createUnitButton.setVisible(false);
        }
        changeBridgeStateButton.setVisible(false);
        if (Building.getGroupNumberByBuildingName(name) == 3) {
            OtherBuildings otherBuildings = (OtherBuildings) building;
            if (otherBuildings.getOtherBuildingsType().equals(OtherBuildingsType.DRAW_BRIDGE)) {
                changeBridgeStateButton.setVisible(true);
            }
        }
    }
}
