package view.menus;

import controller.MenuNames;
import controller.menucontrollers.SelectUnitMenuController;
import view.Command;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            } else if ((matcher = Command.getMatcher(input, Command.ATTACK)) != null) {
                Attack(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.POUR_OIL)) != null) {
                pourOil(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.DIG_TUNNEL)) != null) {
                digTunnel(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.BUILD_EQUIPMENT)) != null) {
                buildEquipment(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.DISBAND_UNIT)) != null) {
                disbandUnit();
            } else if ((matcher = Command.getMatcher(input, Command.BACK_GAME_MENU)) != null) {
                return MenuNames.GAME_MENU;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }
    private static void moveUnit(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        if(!(xMatcher.find() && yMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        int xPosition=Integer.parseInt(xMatcher.group(1));
        int yPosition=Integer.parseInt(yMatcher.group(1));

        System.out.println(SelectUnitMenuController.moveUnit(xPosition,yPosition));
    }

    private static void patrolUnit(Matcher matcher) {
        String input = matcher.group(0);
        Matcher x1Matcher = Pattern.compile("x1\\s+(\\d+)").matcher(input);
        Matcher x2Matcher = Pattern.compile("x2\\s+(\\d+)").matcher(input);
        Matcher y1Matcher = Pattern.compile("y1\\s+(\\d+)").matcher(input);
        Matcher y2Matcher = Pattern.compile("y2\\s+(\\d+)").matcher(input);
        if(!(x1Matcher.find() && x2Matcher.find() && y1Matcher.find() && y2Matcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        int x1 = Integer.parseInt(x1Matcher.group(1));
        int x2 = Integer.parseInt(x2Matcher.group(1));
        int y1 = Integer.parseInt(y1Matcher.group(1));
        int y2 = Integer.parseInt(y2Matcher.group(1));
        System.out.println(SelectUnitMenuController.patrolUnit(x1, y1, x2, y2));
    }

    private static void setStateOfUnit(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        Matcher sMatcher = Pattern.compile("-s\\s+(\\S+)").matcher(input);
        if(!(xMatcher.find() && yMatcher.find() && sMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        int x = Integer.parseInt(xMatcher.group(1));
        int y = Integer.parseInt(yMatcher.group(1));
        String state = sMatcher.group(1);
        if (!(state.equals("standing") || state.equals("defensive") || state.equals("offensive"))) {
            System.out.println("Invalid state!");
            return;
        }
        System.out.println(SelectUnitMenuController.setStateOfUnit(x, y, state));
    }

    private static void Attack(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        if(!(xMatcher.find() && yMatcher.matches())) {
            System.out.println("Invalid command!");
            return;
        }
        int x=Integer.parseInt(xMatcher.group(1));
        int y=Integer.parseInt(yMatcher.group(1));

        System.out.println(SelectUnitMenuController.makeUnitAttacking(x,y));
    }

    private static void pourOil(Matcher matcher) {
        String direction = matcher.group(1);
        if(!(direction.equals("e") || direction.equals("w") || direction.equals("n") || direction.equals("s"))) {
            System.out.println("Invalid command!");
            return;
        }
        System.out.println(SelectUnitMenuController.pourOil(direction));
    }

    private static void digTunnel(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        if(!(xMatcher.find() && yMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        int x = Integer.parseInt(xMatcher.group(1));
        int y = Integer.parseInt(yMatcher.group(1));
        System.out.println(SelectUnitMenuController.digTunnel(x, y));
    }

    private static void buildEquipment(Matcher matcher) {
        String equipmentName = matcher.group(1);
        System.out.println(SelectUnitMenuController.buildEquipment(equipmentName));
    }

    private static void disbandUnit() {
        System.out.println(SelectUnitMenuController.disbandUnit());
    }
}
