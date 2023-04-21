package view;

import com.sun.source.tree.BreakTree;
import controller.MenuNames;
import controller.ProfileMenuController;
import model.AppData;

import java.util.Scanner;

public class ProfileMenu {
    public static MenuNames run(Scanner scanner) {
        String input = scanner.nextLine();
        if(Command.getMatcher(input, Command.REMOVE_SLOGAN) != null) {
            System.out.println(ProfileMenuController.removeSlogan());
        }
        else if (Command.getMatcher(input, Command.BACK_MAIN_MENU) != null) {
            System.out.println("You entered in main menu");
            return MenuNames.MAIN_MENU;
        }
        else if (Command.getMatcher(input, Command.CHANGE_USERNAME) != null) {
            System.out.println(ProfileMenuController.changeUsername(Command.getMatcher(input, Command.CHANGE_USERNAME)));
        }
        else if (Command.getMatcher(input, Command.CHANGE_PASSWORD) != null) {
            System.out.println(ProfileMenuController.changePassword(Command.getMatcher(input, Command.CHANGE_PASSWORD)));
        }
        else if (Command.getMatcher(input, Command.CHANGE_NICKNAME) != null) {
            System.out.println(ProfileMenuController.changeNickname(Command.getMatcher(input, Command.CHANGE_NICKNAME)));
        }
        else if (Command.getMatcher(input, Command.CHANGE_EMAIL) != null) {
            System.out.println(ProfileMenuController.changeEmail(Command.getMatcher(input, Command.CHANGE_EMAIL)));
        }
        else if (Command.getMatcher(input, Command.CHANGE_SLOGAN) != null) {
            System.out.println(ProfileMenuController.changeSlogan(Command.getMatcher(input, Command.CHANGE_SLOGAN)));
        }
        else if (Command.getMatcher(input, Command.DISPLAY_HIGH_SCORE) != null) {
            System.out.println(ProfileMenuController.displayHighScore());
        }
        else if (Command.getMatcher(input, Command.DISPLAY_RANK) != null) {
            System.out.println(ProfileMenuController.displayRank());
        }
        else if (Command.getMatcher(input, Command.DISPLAY_SLOGAN) != null) {
            System.out.println(ProfileMenuController.displaySlogan());
        }
        else if (Command.getMatcher(input, Command.DISPLAY_PROFILE) != null) {
            System.out.println(ProfileMenuController.displayProfile());
        }
        else {
            System.out.println("Invalid command!");
        }
        return MenuNames.PROFILE_MENU;
    }

}
