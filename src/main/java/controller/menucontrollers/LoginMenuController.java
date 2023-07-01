package controller.menucontrollers;

import javafx.scene.control.Alert;
import model.AppData;
import model.SaveAndLoad;
import model.User;
import view.menus.LoginMenu;
import view.menus.RegisterMenu;
import view.menus.SecurityQuestionMenu;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenuController {
    private static User currentUser;

    public static int checkWeakPassword(String password) {
        Matcher matcherCapital = Pattern.compile("[A-Z]").matcher(password);
        Matcher matcherSmall = Pattern.compile("[a-z]").matcher(password);
        Matcher matcherNumber = Pattern.compile("\\d").matcher(password);
        Matcher matcherOdd = Pattern.compile("[*'!@#&%_\\-=~+()^{}\"?]").matcher(password);
        if (password.length() < 6) {
            return 0;
        }
        if (!(matcherCapital.find() && matcherSmall.find() && matcherNumber.find() && matcherOdd.find())) {
            return 1;
        }
        return 2;
    }

    public static int checkEmailFormat(String email) {
        Pattern patternOfFormatOfEmail = Pattern.compile("\\S+@\\S+\\.\\S+");
        Matcher matcherOfFormatOfEmail = patternOfFormatOfEmail.matcher(email);
        if (matcherOfFormatOfEmail.matches()) {
            return 1;
        }
        return 0;
    }

    public static String createRandomPassword() {
        String output = "";
        Random random = new Random();
        int[] n1 = {26, 26, 26, 26, 10, 10, 10, 15}, n2 = {65, 65, 65, 97, 48, 48, 48, 33};
        for (int i = 0; i < 8; i++)
            output = addCharToPassword(random, output, n1[i], n2[i]);
        return output;
    }

    private static String addCharToPassword(Random random, String output, int n1, int n2) {
        int myRandom = random.nextInt() % n1;
        myRandom *= myRandom;
        int charAscii = (myRandom % n1) + n2;
        return output + (char) charAscii;
    }

    public static String createRandomSlogan() {
        Random random = new Random();
        int myRandom = random.nextInt();
        myRandom = myRandom % 5;
        if (myRandom == 0) {
            return "This day, is a very bad day for our enemies!";
        }
        else if (myRandom == 1) {
            return "Our empire destroys its enemies!";
        }
        else if (myRandom == 2) {
            return "You are just a looser!";
        }
        else if (myRandom == 3) {
            return "We are winner!";
        }
        return "Us against whole of the world!";
    }

    public static int checkEmptyFilled
            (Matcher matcherExistSlogan, Matcher matcherUsername, Matcher matcherPassword,
             Matcher matcherNickname, Matcher matcherEmail, Matcher matcherSlogan) {
        if (!matcherUsername.find()) {
            return 1;
        }
        if (!matcherPassword.find()) {
            return 2;
        }
        if (!matcherNickname.find()) {
            return 3;
        }
        if (!matcherEmail.find()) {
            return 4;
        }
        if (matcherExistSlogan.find() && !matcherSlogan.find()) {
            return 5;
        }
        return 0;
    }

    public static String getSlogan(Matcher matcherExistSlogan, Matcher matcherSlogan) {
        String slogan = "";
        if (matcherExistSlogan.matches()) {
            if (!matcherSlogan.group(1).equals("random")) {
                slogan = matcherSlogan.group(1);
            }
            else {
                slogan = createRandomSlogan();
            }
        }
        return slogan;
    }

    public static int checkInvalidCommand(String input) {
        Matcher matcherExistUsername = Pattern.compile("-u").matcher(input);
        Matcher matcherExistPassword = Pattern.compile("-p").matcher(input);
        Matcher matcherExistNickname = Pattern.compile("-n").matcher(input);
        Matcher matcherExistEmail = Pattern.compile("-email").matcher(input);
        if (!(matcherExistUsername.find() && matcherExistPassword.find() &&
                matcherExistNickname.find() && matcherExistEmail.find())) {
            return 1;
        }
        return 0;
    }

    public static int checkFormatOfUsername(String username) {
        Matcher matcherCheckUsername = Pattern.compile("\\w+").matcher(username);
        if (matcherCheckUsername.matches()) {
            return 1;
        }
        return 0;
    }

    public static void createUser(String username, String password, String email, String nickname, String slogan, boolean checkSlogan) throws Exception {
        int stopCreate = 0;
        if (username.length() == 0) {
            RegisterMenu.getUsernameText().setText("Username field was empty!");
            stopCreate = 1;
        }
        if (password.length() == 0) {
            RegisterMenu.getPasswordText().setText("Password field was empty!");
            stopCreate = 1;
        }
        if (email.length() == 0) {
            RegisterMenu.getEmailText().setText("Email field was empty!");
            stopCreate = 1;
        }
        if (nickname.length() == 0) {
            RegisterMenu.getNicknameText().setText("Nickname field was empty!");
            stopCreate = 1;
        }
        if (slogan.length() == 0 && checkSlogan) {
            RegisterMenu.getSloganText().setText("Slogan field was empty!");
            stopCreate = 1;
        }
        if (checkFormatOfUsername(username) == 0 && username.length() != 0) {
            RegisterMenu.getUsernameText().setText("Format of your username was incorrect!");
            stopCreate = 1;
        }
        if (checkWeakPassword(password) == 0 && password.length() != 0) {
            RegisterMenu.getPasswordText().setText("Your password was short!");
            stopCreate = 1;
        }
        else if (checkWeakPassword(password) == 1 && password.length() != 0) {
            RegisterMenu.getPasswordText().setText("Your password was week!");
            stopCreate = 1;
        }
        if (checkEmailFormat(email) == 0 && email.length() != 0) {
            RegisterMenu.getEmailText().setText("Your email hadn't true format!");
            stopCreate = 1;
        }
        if (AppData.getUserByUsername(username) != null) {
            RegisterMenu.getUsernameText().setText("This username was already exist!");
            stopCreate = 1;
        }
        if (AppData.getUserByEmail(email) != null) {
            RegisterMenu.getEmailText().setText("This email was already exist!");
            stopCreate = 1;
        }
        if (stopCreate == 1) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successful Operation");
        alert.setContentText("User was successfully created");
        alert.showAndWait();
        User user = new User(username, SaveAndLoad.hashString(password), nickname, email, slogan, "");
        AppData.addUser(user);
        currentUser = user;
        new SecurityQuestionMenu().start(AppData.getStage());
    }

    public static String login(String username, String password) {
        if (AppData.getUserByUsername(username) == null) {
            return "User with this username doesn't exist!";
        }
        else if (!AppData.getUserByUsername(username).getPassword().equals(SaveAndLoad.hashString(password))) {
            return "Username and password didn't match!";
        }
        AppData.setCurrentUser(AppData.getUserByUsername(username));
        AppData.setDelayInLogin(0);
        return "user logged in successfully!";
    }

    public static String forgottenPassword(String username) {
        if (AppData.getUserByUsername(username) == null) {
            return "User with this username doesn't exist!";
        }
        String[] securityQuestion = AppData.getUserByUsername(username).getSecurityQuestion().split(" ");
        int numberOfQuestion = Integer.parseInt(securityQuestion[0]);
        String answer = securityQuestion[1];
        String input = LoginMenu.askSecurityQuestion(numberOfQuestion);
        if (input.equals(answer)) {
            String newPassword = LoginMenu.changePasswordWithSecurityQuestion();
            Matcher passwordMatcher = Pattern.compile("\\s*(\\S+)\\s+(\\S+)\\s*").matcher(newPassword);
            if (!passwordMatcher.matches()) {
                return "Invalid command!";
            }
            if (!passwordMatcher.group(1).equals(passwordMatcher.group(2))) {
                return "Please enter password confirmation correctly!";
            }
            String password = passwordMatcher.group(1);
            if (checkWeakPassword(password) != 2) {
                return "Your new password is weak!";
            }
            User user=AppData.getUserByUsername(username);
            user.setPassword(SaveAndLoad.hashString(password));
            AppData.updateUserData(user);

            return "Password changed successfully";
        }

        return "Wrong answer!";
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        LoginMenuController.currentUser = currentUser;
    }
}
