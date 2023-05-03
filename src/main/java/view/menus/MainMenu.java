package view.menus;

import controller.MenuNames;
import controller.menucontrollers.GameMenuController;
import controller.menucontrollers.MainMenuController;
import model.AppData;
import model.GameData;
import model.User;
import view.Command;

import java.util.Scanner;

public class MainMenu {
    private static User loggedInUser;
    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }
    public static MenuNames run(Scanner scanner) {
        String input = scanner.nextLine();
        if (Command.getMatcher(input, Command.LOGOUT) != null) {
            System.out.println("User logged out");
            AppData.setStayLoggedIn(0);
            return MenuNames.LOGIN_MENU;
        }
        else if (Command.getMatcher(input, Command.ENTER_PROFILE_MENU) != null) {
            System.out.println("You entered profile menu successfully");
            return MenuNames.PROFILE_MENU;
        }
        else if (Command.getMatcher(input, Command.START_GAME) != null) {
            MainMenuController.createGameData(loggedInUser);
            System.out.println("Please choose your opponents and map");
            return MenuNames.PRE_GAME_MENU;
        }
        else {
            System.out.println("Invalid command!");
        }
        return MenuNames.MAIN_MENU;
    }

}
