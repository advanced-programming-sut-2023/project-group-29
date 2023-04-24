package view;

import controller.LoginMenuController;
import controller.MenuNames;
import model.AppData;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {
    public static MenuNames run(Scanner scanner) {
        Matcher matcher;
        String input = scanner.nextLine();
        if((matcher = Command.getMatcher(input, Command.USER_CREATE)) != null) {
            System.out.println(LoginMenuController.createUser(matcher));
        }
        else if ((matcher = Command.getMatcher(input, Command.EXIT)) != null) {
            return MenuNames.EXIT;
        }
        else if ((matcher = Command.getMatcher(input, Command.LOGIN)) != null) {
            String output = LoginMenuController.login(matcher);
            System.out.println(output);
            if (output.equals("user logged in successfully!")) {
                AppData.setDelayInLogin(0);
                return MenuNames.MAIN_MENU;
            }
            if (output.equals("Username and password didn't match!")) {
                AppData.setDelayInLogin(AppData.getDelayInLogin() + 5000);
                try{
                    Thread.sleep(AppData.getDelayInLogin());

                }catch(Exception e){}
            }
        }
        else if ((matcher = Command.getMatcher(input, Command.FORGET_PASSWORD)) != null) {
            System.out.println(LoginMenuController.forgottenPassword(matcher));
        }
        else {
            System.out.println("Invalid command!");
        }
        return MenuNames.LOGIN_MENU;
    }
}
