package view.menus;

import controller.MenuNames;
import controller.menucontrollers.SelectBuildingMenuController;
import view.Command;
import view.messages.SelectBuildingMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectBuildingMenu {
    public static MenuNames run(Scanner scanner) {
        System.out.println("You have entered select building menu");
        System.out.println(SelectBuildingMenuController.getBuildingHp());
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if ((matcher = Command.getMatcher(input, Command.CREATE_UNIT)) != null) {
                createUnit(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.REPAIR_BUILDING)) != null) {
                repairBuilding();
            } else if (Command.getMatcher(input, Command.CHANGE_BRIDGE_STATE) != null) {
                changeBridgeState();
            } else if ((matcher = Command.getMatcher(input, Command.BACK_GAME_MENU)) != null) {
                return MenuNames.GAME_MENU;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private static void changeBridgeState() {
        SelectBuildingMenuMessages result = SelectBuildingMenuController.changeBridgeState();
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
        SelectBuildingMenuMessages result = SelectBuildingMenuController.createUnit(unitType, count);
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
        SelectBuildingMenuMessages result = SelectBuildingMenuController.repairBuilding();
        switch (result) {
            case LACK_OF_STONE -> System.out.println("You don't have enough stone to repair your building!");
            case ENEMY_IS_NEAR -> System.out.println("You can't repair your buildings while Enemies are near them!");
            case SUCCESS -> System.out.println("Your building was successfully repaired!");
        }
    }
}
