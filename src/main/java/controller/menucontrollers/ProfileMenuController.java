package controller.menucontrollers;

import model.AppData;
import model.SaveAndLoad;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenuController {
    public static String changeUsername(String username) {
        Pattern patternCheckUsername = Pattern.compile("\\w+");
        Matcher matcherCheckUsername = patternCheckUsername.matcher(username);
        if (!matcherCheckUsername.matches()) {
            return "Check format of your username";
        }
        if (AppData.getUserByUsername(username) != null) {
            return "This username is already exist!";
        }
        AppData.getCurrentUser().setUsername(username);
        AppData.updateUserData(AppData.getCurrentUser());
        return "Username changed";
    }

    public static String changePassword(String oldPass, String newPass) {

        if (!AppData.getCurrentUser().getPassword().equals(SaveAndLoad.hashString(oldPass))) {
            return "Enter your old password correctly!";
        }
        if (oldPass.equals(newPass)) {
            return "Choose different new password!";
        }
        if (LoginMenuController.checkWeakPassword(newPass) != 2) {
            return "Your new password is weak!";
        }

        AppData.getCurrentUser().setPassword(SaveAndLoad.hashString(newPass));
        AppData.updateUserData(AppData.getCurrentUser());

        return "Password changed";
    }

    public static String changeNickname(String nickname) {
        AppData.getCurrentUser().setNickname(nickname);
        AppData.updateUserData(AppData.getCurrentUser());

        return "Nickname changed";
    }

    public static String changeEmail(String email) {
        if (LoginMenuController.checkEmailFormat(email) == 0) {
            return "Check format of your email";
        }
        AppData.getCurrentUser().setEmail(email);
        AppData.updateUserData(AppData.getCurrentUser());

        return "Email changed";
    }

    public static String changeSlogan(String slogan) {
        AppData.getCurrentUser().setSlogan(slogan);
        AppData.updateUserData(AppData.getCurrentUser());

        return "Slogan changed";
    }

    public static String removeSlogan() {
        AppData.getCurrentUser().setSlogan("");
        AppData.updateUserData(AppData.getCurrentUser());

        return "Your slogan removed";
    }

    public static void setMyAvatar(File file) {
        AppData.getCurrentUser().setAvatar(file.getAbsolutePath());
        AppData.updateUserData(AppData.getCurrentUser());
    }

}
