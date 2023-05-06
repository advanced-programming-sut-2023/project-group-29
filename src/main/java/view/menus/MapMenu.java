package view.menus;

import controller.MenuNames;
import controller.menucontrollers.GameMenuController;
import controller.menucontrollers.MapMenuController;
import view.Command;
import view.messages.GameMenuMessages;
import view.messages.MapMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu {
    public static MenuNames run(Scanner scanner) {
        showMap();
        while(true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if ((matcher=Command.getMatcher(input, Command.SHOW_MAP)) != null) {
                showMap(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.MOVE_MAP)) != null) {
                moveMap(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.SET_TEXTURE)) != null) {
                setTexture(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.CLEAR)) != null) {
                clear(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.DROP_ROCK)) != null) {
                dropRock(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.DROP_TREE)) != null) {
                dropTree(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.DROP_BUILDING)) != null) {
                dropBuilding(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.DROP_UNIT)) != null) {
                dropUnit(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.BACK_GAME_MENU)) != null) {
                return MenuNames.GAME_MENU;
            }
            else {
                System.out.println("Invalid command!");
            }
        }
    }

    private static void moveMap(Matcher matcher) {
    }

    private static void setTexture(Matcher matcher) {

    }

    private static void clear(Matcher matcher) {
    }

    private static void dropRock(Matcher matcher) {

    }

    private static void dropTree(Matcher matcher) {

    }

    private static void dropUnit(Matcher matcher) {

    }

    private static void showMap(Matcher matcher) {

        int positionX=Integer.parseInt(matcher.group("xAmount"));
        int positionY=Integer.parseInt(matcher.group("yAmount"));

        System.out.println(MapMenuController.showMap(positionX,positionY));
    }

    private static void showMap(){
        System.out.println(MapMenuController.showMap());
    }

    private static void dropBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("xPosition"));
        int y = Integer.parseInt(matcher.group("yPosition"));
        String buildingName = matcher.group("type");

        //todo abbasfar handle isAdmin conditions
        MapMenuMessages result = MapMenuController.dropBuilding(x, y, buildingName, true);
        switch (result) {
            case TWO_MAIN_KEEP -> System.out.println("You aren't allowed to have two main keeps!");
            case INVALID_INDEX -> System.out.println("You have chosen an Invalid amount of x or y!");
            case INVALID_TYPE -> System.out.println("This type of building doesn't exist!");
            case UNCONNECTED_STOREROOMS -> System.out.println("Your storerooms must be connected to each other!");
            case IMPROPER_CELL_TYPE -> System.out.println("This cell is improper for dropping this type of building!");
            case FULL_CELL -> System.out.println("Another building has been already dropped here!");
            case LACK_OF_RESOURCES -> System.out.println("You don't have enough resources to build this building!");
            case SUCCESSFUL -> System.out.println("The building was dropped successfully!");
        }
    }
}
