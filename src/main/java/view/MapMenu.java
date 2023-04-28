package view;

import controller.MenuNames;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu {
    public static MenuNames run(Scanner scanner) {
        Matcher matcher;
        String input = scanner.nextLine();
        if (Command.getMatcher(input, Command.SHOW_MAP) != null) {
            showMap();
        } else if ((matcher = Command.getMatcher(input, Command.MOVE_MAP)) != null) {
            moveMap(matcher);
        } else if ((matcher = Command.getMatcher(input, Command.SET_TEXTURE)) != null) {
            setTexture(matcher);
        } else if ((matcher = Command.getMatcher(input, Command.CLEAR)) != null) {
            clear(matcher);
        } else if ((matcher = Command.getMatcher(input, Command.DROP_ROCK)) != null) {
            dropRock(matcher);
        } else if ((matcher = Command.getMatcher(input, Command.DROP_TREE)) != null) {
            dropTree(matcher);
        } else if ((matcher = Command.getMatcher(input, Command.DROP_BUILDING)) != null) {
            dropBuilding(matcher);
        } else if ((matcher = Command.getMatcher(input, Command.DROP_UNIT)) != null) {
            dropUnit(matcher);
        } else {
            System.out.println("Invalid command!");
        }

        return null;
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

    private static void dropBuilding(Matcher matcher) {

    }

    private static void dropUnit(Matcher matcher) {

    }

    private static void showMap() {
    }
}
