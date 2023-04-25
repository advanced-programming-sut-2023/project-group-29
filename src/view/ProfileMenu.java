package view;

import com.sun.source.tree.BreakTree;
import controller.MenuNames;
import controller.ProfileMenuController;
import model.AppData;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {
    public static MenuNames run(Scanner scanner) {
        Matcher matcher;
        String input = scanner.nextLine();
        if(Command.getMatcher(input, Command.REMOVE_SLOGAN) != null) {
            System.out.println(ProfileMenuController.removeSlogan());
        }
        else if (Command.getMatcher(input, Command.BACK_MAIN_MENU) != null) {
            System.out.println("You entered in main menu");
            return MenuNames.MAIN_MENU;
        }
        else if ((matcher = Command.getMatcher(input, Command.CHANGE_USERNAME)) != null) {
            System.out.println(ProfileMenuController.changeUsername(matcher));
        }
        else if ((matcher = Command.getMatcher(input, Command.CHANGE_PASSWORD)) != null) {
            System.out.println(ProfileMenuController.changePassword(matcher));
        }
        else if ((matcher = Command.getMatcher(input, Command.CHANGE_NICKNAME)) != null) {
            System.out.println(ProfileMenuController.changeNickname(matcher));
        }
        else if ((matcher = Command.getMatcher(input, Command.CHANGE_EMAIL)) != null) {
            System.out.println(ProfileMenuController.changeEmail(matcher));
        }
        else if ((matcher = Command.getMatcher(input, Command.CHANGE_SLOGAN)) != null) {
            System.out.println(ProfileMenuController.changeSlogan(matcher));
        }
        else if ((matcher = Command.getMatcher(input, Command.DISPLAY_HIGH_SCORE)) != null) {
            System.out.println(ProfileMenuController.displayHighScore());
        }
        else if ((matcher = Command.getMatcher(input, Command.DISPLAY_RANK)) != null) {
            System.out.println(ProfileMenuController.displayRank());
        }
        else if ((matcher = Command.getMatcher(input, Command.DISPLAY_SLOGAN)) != null) {
            System.out.println(ProfileMenuController.displaySlogan());
        }
        else if ((matcher = Command.getMatcher(input, Command.DISPLAY_PROFILE)) != null) {
            System.out.println(ProfileMenuController.displayProfile());
        }
        else {
            System.out.println("Invalid command!");
        }
        return MenuNames.PROFILE_MENU;
    }

}
