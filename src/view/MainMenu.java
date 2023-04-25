package view;

import controller.MenuNames;
import model.AppData;

import java.util.Scanner;

public class MainMenu {
    public static MenuNames run(Scanner scanner) {
        String input = scanner.nextLine();
        if (Command.getMatcher(input, Command.LOGOUT) != null) {
            System.out.println("User logged out");
            AppData.setStayLoggedIn(0);
            return MenuNames.LOGIN_MENU;
        } else if (Command.getMatcher(input, Command.ENTER_PROFILE_MENU) != null) {
            System.out.println("You entered in profile menu successfully");
            return MenuNames.PROFILE_MENU;
        } else {
            System.out.println("Invalid command!");
        }
        return MenuNames.MAIN_MENU;
    }
}
