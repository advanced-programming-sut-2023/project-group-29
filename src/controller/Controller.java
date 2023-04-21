package controller;

import view.GameMenu;
import view.LoginMenu;
import view.MainMenu;
import view.ProfileMenu;

import java.util.Scanner;

public class Controller {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        MenuNames menuNames = MenuNames.LOGIN_MENU;

        while (true) {
            switch (menuNames) {
                case LOGIN_MENU:
                    menuNames = LoginMenu.run(scanner);
                    break;
                case GAME_MENU:
                    menuNames = GameMenu.run(scanner);
                    break;
                case MAIN_MENU:
                    menuNames = MainMenu.run(scanner);
                    break;
                case PROFILE_MENU:
                    menuNames = ProfileMenu.run(scanner);
                    break;
                case EXIT:
                    return;
            }
        }
    }
}
