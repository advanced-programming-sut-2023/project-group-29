package view.menus;

import controller.MenuNames;
import controller.menucontrollers.BuildingFunctions;
import controller.menucontrollers.GameController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.Command;
import view.messages.SelectBuildingMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectBuildingMenu {
    @FXML
    private Button createUnitButton;
    @FXML
    private Button repairButton;

    public void exitPopUp(MouseEvent mouseEvent) {
        GameController.getGameData().getGameGraphicFunctions().exitPopUp();
    }

    @FXML
    public void initialize() {
        repairButton.setOnMouseClicked(mouseEvent -> {
            SelectBuildingMenuMessages result = BuildingFunctions.repairBuilding();
            //todo result
        });
        createUnitButton.setOnMouseClicked(mouseEvent -> {
            //todo enter drop unit menu
        });
    }

    public void hideCreateUnit(){
        createUnitButton.setVisible(false);
    }
    private static void changeBridgeState() {
        SelectBuildingMenuMessages result = BuildingFunctions.changeBridgeState();
        switch (result) {
            case UNRELATED -> System.out.println("You can't change state of this building!");
            case SUCCESS -> System.out.println("Your unit was successfully created!");
        }
    }

    private static void createUnit(Matcher matcher) {
        String input = matcher.group(0);
        Matcher typeMatcher = Pattern.compile("-t\\s+(\\S+)").matcher(input);
        Matcher countMatcher = Pattern.compile("-c\\s+(\\d+)").matcher(input);
        if (!(typeMatcher.find() && countMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        String unitType = typeMatcher.group(1);
        int count = Integer.parseInt(countMatcher.group(1));
        SelectBuildingMenuMessages result = BuildingFunctions.createUnit(unitType, count);
        switch (result) {
            case INVALID_TYPE -> System.out.println("We have no unit with this name!");
            case LACK_OF_COINS -> System.out.println("You don't have enough coin to create this unit!");
            case LACK_OF_WEAPON -> System.out.println("You don't have proper weapon to equip this unit!");
            case LACK_OF_HUMAN -> System.out.println("There is no person to become your unit!");
            case UNRELATED -> System.out.println("You should try creating this unit in other buildings!");
            case SUCCESS -> System.out.println("Your unit was successfully created!");
        }
    }

    private static void repairBuilding() {
        SelectBuildingMenuMessages result = BuildingFunctions.repairBuilding();
        switch (result) {
            case LACK_OF_STONE -> System.out.println("You don't have enough stone to repair your building!");
            case ENEMY_IS_NEAR -> System.out.println("You can't repair your buildings while Enemies are near them!");
            case SUCCESS -> System.out.println("Your building was successfully repaired!");
        }
    }

    public static MenuNames run(Scanner scanner) {
        return null;
    }

}
