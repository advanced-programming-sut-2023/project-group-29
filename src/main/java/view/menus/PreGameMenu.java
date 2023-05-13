package view.menus;

import controller.MenuNames;
import controller.menucontrollers.PreGameMenuController;
import view.Command;
import view.messages.PreGameMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class PreGameMenu {
    public static MenuNames run(Scanner scanner) {
        System.out.println("Please choose your opponents and map:");
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;
            if ((matcher = Command.getMatcher(input, Command.ADD_PLAYER_TO_GAME)) != null) {
                addPlayerToGame(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.CHOOSE_MAP)) != null) {
                chooseMap(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.READY)) != null) {
                if (notReady()) continue;
                return MenuNames.GAME_MENU;
            } else if ((matcher = Command.getMatcher(input, Command.CANCEL)) != null) {
                System.out.println("The game was canceled!");
                return MenuNames.MAIN_MENU;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private static void addPlayerToGame(Matcher matcher) {
        String username = matcher.group("username");
        PreGameMenuMessages result = PreGameMenuController.addUserToGame(username);
        switch (result) {
            case INVALID_USER -> System.out.println("No user exists with this username!");
            case USER_EXIST -> System.out.println("You have already added this user!");
            case FILLED_TO_CAPACITY -> System.out.println("The maximum number of player is 8!");
            case SUCCESS -> System.out.println("The player was added successfully!");
        }
    }

    private static void chooseMap(Matcher matcher) {
        int index = Integer.parseInt(matcher.group("index"));
        PreGameMenuMessages result = PreGameMenuController.chooseMap(index);
        switch (result) {
            case FEW_PLAYER -> System.out.println("Number of players is not enough for this map!");
            case OUT_OF_RANGE -> System.out.println("No map exists with this index!");
            case SUCCESS -> System.out.println("Map is chosen successfully!");
        }
    }

    private static boolean notReady() {
        PreGameMenuMessages result = PreGameMenuController.isGameDataReady();
        switch (result) {
            case FEW_PLAYER -> {
                System.out.println("Playing with yourself will not be interesting!");
                return true;
            }
            case NOT_CHOSEN_MAP -> {
                System.out.println("You haven't still chosen the map!");
                return true;
            }
            case READY -> System.out.println("The game started. GOOD LUCK!");
        }
        return false;
    }
}
