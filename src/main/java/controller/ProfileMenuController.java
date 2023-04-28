package controller;

import model.AppData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenuController {
    public static String changeUsername(Matcher matcher) {
        String username = matcher.group(1);
        Pattern patternCheckUsername = Pattern.compile("\\w+");
        Matcher matcherCheckUsername = patternCheckUsername.matcher(username);
        if (!matcherCheckUsername.matches()) {
            return "Check format of your username";
        }
        if (AppData.getUserByUsername(username) != null) {
            return "This username is already exist!";
        }
        AppData.getCurrentUser().setUsername(username);
        return "Username changed";
    }

    public static String changePassword(Matcher matcher) {
        String input = matcher.group(0);
        Pattern oldPassPattern = Pattern.compile("-o (\\S+)");
        Pattern newPassPattern = Pattern.compile("-n (\\S+)");
        Matcher oldPassMatcher = oldPassPattern.matcher(input);
        Matcher newPassMatcher = newPassPattern.matcher(input);
        if (!(oldPassMatcher.find() && newPassMatcher.find())) {
            return "Invalid command!";
        }
        String oldPass = oldPassMatcher.group(1);
        String newPass = newPassMatcher.group(1);
        //TODO CAPTCHA
        if (!AppData.getCurrentUser().getPassword().equals(oldPass)) {
            return "Enter your old password correctly!";
        }
        if (oldPass.equals(newPass)) {
            return "Choose different new password!";
        }
        if (LoginMenuController.checkWeakPassword(newPass) != 2) {
            return "Your new password is weak!";
        }

        AppData.getCurrentUser().setPassword(newPass);
        return "Password changed";
    }

    public static String changeNickname(Matcher matcher) {
        String nickname = matcher.group(1);
        AppData.getCurrentUser().setNickname(nickname);
        return "Nickname changed";
    }

    public static String changeEmail(Matcher matcher) {
        String email = matcher.group(1);
        if (LoginMenuController.checkEmailFormat(email) == 0) {
            return "Check format of your email";
        }
        AppData.getCurrentUser().setEmail(email);
        return "Email changed";
    }

    public static String changeSlogan(Matcher matcher) {
        String slogan = matcher.group(1);
        AppData.getCurrentUser().setSlogan(slogan);
        return "Slogan changed";
    }

    public static String removeSlogan() {
        AppData.getCurrentUser().setSlogan("");
        return "Your slogan removed";
    }

    public static int displayHighScore() {
        return AppData.getCurrentUser().getHighScore();
    }

    public static int displayRank() {
        return AppData.rankOfUser(AppData.sortUserByHighScore(), AppData.getCurrentUser().getUsername());
    }

    public static String displaySlogan() {
        if (AppData.getCurrentUser().getSlogan().length() == 0) {
            return "Slogan is empty!";
        }
        return AppData.getCurrentUser().getSlogan();
    }

    public static String displayProfile() {
        String output = "Username: " + AppData.getCurrentUser().getUsername() + "\n";
        output = output + "Nickname: " + AppData.getCurrentUser().getNickname() + "\n";
        output = output + "Email: " + AppData.getCurrentUser().getEmail() + "\n";
        output = output + "Slogan: " + AppData.getCurrentUser().getSlogan() + "\n";
        output = output + "Highscore: " + AppData.getCurrentUser().getHighScore() + "\n";
        output = output + "Rank: " + AppData.rankOfUser(AppData.sortUserByHighScore(),
                AppData.getCurrentUser().getUsername());
        return output;
    }

}