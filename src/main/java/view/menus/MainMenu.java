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
    public static MenuNames run(Scanner scanner) {
        System.out.println("You have entered main menu");
        while (true) {
            String input = scanner.nextLine();
            if (Command.getMatcher(input, Command.LOGOUT) != null) {
                System.out.println("User logged out");
                AppData.setStayLoggedIn(0);
                return MenuNames.LOGIN_MENU;
            } else if (Command.getMatcher(input, Command.ENTER_PROFILE_MENU) != null) {
                return MenuNames.PROFILE_MENU;
            } else if (Command.getMatcher(input, Command.START_GAME) != null) {
                MainMenuController.createGameData();
                return MenuNames.PRE_GAME_MENU;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

}
