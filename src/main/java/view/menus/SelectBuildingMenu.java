package view.menus;

import controller.MenuNames;
import controller.menucontrollers.SelectBuildingMenuController;
import model.buildings.Building;
import view.Command;
import view.messages.SelectBuildingMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SelectBuildingMenu {
    public static MenuNames run(Scanner scanner) {
        System.out.println("You have entered select building menu");
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if ((matcher = Command.getMatcher(input, Command.CREATE_UNIT)) != null) {
                createUnit();
            } else if ((matcher = Command.getMatcher(input, Command.REPAIR_BUILDING)) != null) {
                repairBuilding();
            } else if ((matcher = Command.getMatcher(input, Command.MOVE_UNIT)) != null) {
                moveUnit(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.PATROL_UNIT)) != null) {
                patrolUnit(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.SET_STATE_OF_UNIT)) != null) {
                setStateOfUnit(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.MAKE_UNIT_ATTACKING)) != null) {
                makeUnitAttacking(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.POUR_OIL)) != null) {
                pourOil(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.DIG_TUNNEL)) != null) {
                digTunnel(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.BUILD_EQUIPMENT)) != null) {
                buildEquipment(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.DISBAND_UNIT)) != null) {
                disbandUnit(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.BACK_GAME_MENU)) != null) {
                return MenuNames.GAME_MENU;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private static void createUnit() {
        //TODO: abbasfar kamel kon
    }

    private static void repairBuilding() {
        SelectBuildingMenuMessages result = SelectBuildingMenuController.repairBuilding();
        switch (result){
            case LACK_OF_STONE -> System.out.println("You don't have enough stone to repair your building!");
            case ENEMY_IS_NEAR -> System.out.println("You can't repair your buildings while Enemies are near them!");
            case SUCCESS -> System.out.println("Your building was successfully repaired!");
        }

    }

    private static void moveUnit(Matcher matcher) {

    }

    private static void patrolUnit(Matcher matcher) {
    }

    private static void setStateOfUnit(Matcher matcher) {

    }

    private static void makeUnitAttacking(Matcher matcher) {

    }

    private static void pourOil(Matcher matcher) {

    }

    private static void digTunnel(Matcher matcher) {
    }

    private static void buildEquipment(Matcher matcher) {

    }

    private static void disbandUnit(Matcher matcher) {

    }
}
