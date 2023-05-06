package view.menus;

import controller.MenuNames;
import controller.menucontrollers.SelectUnitMenuController;
import view.Command;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SelectUnitMenu {
    public static MenuNames run(Scanner scanner) {
        System.out.println("You have entered select unit menu");
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if ((matcher = Command.getMatcher(input, Command.MOVE_UNIT)) != null) {
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
    private static void moveUnit(Matcher matcher) {
        int xPosition=Integer.parseInt(matcher.group("xPosition"));
        int yPosition=Integer.parseInt(matcher.group("yPosition"));

        System.out.println(SelectUnitMenuController.moveUnit(xPosition,yPosition));
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
