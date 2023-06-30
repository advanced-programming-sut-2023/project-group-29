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
        SaveAndLoad.saveData(AppData.getUsers(), AppData.getUsersDataBaseFilePath());
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
        SaveAndLoad.saveData(AppData.getUsers(), AppData.getUsersDataBaseFilePath());
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
            AppData.getUserByUsername(username).setPassword(SaveAndLoad.hashString(password));
            SaveAndLoad.saveData(AppData.getUsers(), AppData.getUsersDataBaseFilePath());
            return "Password changed successfully";
        }

        return "Wrong answer!";
    }

    public static String[] graphicNumbers(int x) {
        String[] zero = {" 0000 ", "00  00", "00  00", "00  00", " 0000 "};
        String[] one = {"1111  ", "  11  ", "  11  ", "  11  ", "111111"};
        String[] two = {" 2222 ", "    22", "   22 ", "  22  ", "222222"};
        String[] three = {" 3333 ", "33  33", "   333", "33  33", " 3333 "};
        String[] four = {"44  44", "44  44", "444444", "    44", "    44"};
        String[] five = {"555555", "55    ", "555555", "    55", "555555"};
        String[] six = {" 6666 ", "66    ", "66666 ", "66  66", " 6666 "};
        String[] seven = {"777777", "   77 ", "  77  ", " 77   ", "77    "};
        String[] eight = {" 8888 ", "88  88", " 8888 ", "88  88", " 8888 "};
        String[] nine = {" 9999 ", "99  99", " 99999", "    99", " 9999 "};

        if (x == 1) {
            return one;
        }
        else if (x == 2) {
            return two;
        }
        else if (x == 3) {
            return three;
        }
        else if (x == 4) {
            return four;
        }
        else if (x == 5) {
            return five;
        }
        else if (x == 6) {
            return six;
        }
        else if (x == 7) {
            return seven;
        }
        else if (x == 8) {
            return eight;
        }
        else if (x == 9) {
            return nine;
        }
        else if (x == 0) {
            return zero;
        }
        return null;
    }

    public static String captcha() {
        Random random = new Random();
        int numberOfNumbers = random.nextInt();
        numberOfNumbers = numberOfNumbers % 8;
        numberOfNumbers++;
        if (numberOfNumbers < 4) {
            numberOfNumbers = 4;
        }
        String[] output = new String[5];
        int myCaptcha = 0;
        int result = 0;
        for (int i = 0; i < numberOfNumbers; i++) {
            myCaptcha = random.nextInt();
            myCaptcha = (myCaptcha * myCaptcha) % 10;
            if (myCaptcha < 0) {
                myCaptcha = -myCaptcha;
            }
            result = (result * 10) + myCaptcha;
            String[] y1 = graphicNumbers(myCaptcha);
            int randomSpace;
            if (i == 0) {
                for (int j = 0; j < 5; j++) {
                    randomSpace = (random.nextInt() % 5) + 1;
                    output[j] = y1[j] + switch (randomSpace) {
                        case 1 -> "*    ";
                        case 2 -> " *   ";
                        case 3 -> "  *  ";
                        case 4 -> "   * ";
                        case 5 -> "    *";
                        default -> "     ";
                    };
                }
            }
            else {
                for (int j = 0; j < 5; j++) {
                    randomSpace = (random.nextInt() % 5) + 1;
                    output[j] += y1[j] + switch (randomSpace) {
                        case 1 -> "*    ";
                        case 2 -> " *   ";
                        case 3 -> "  *  ";
                        case 4 -> "   * ";
                        case 5 -> "    *";
                        default -> "     ";
                    };

                }
            }

        }

        return output[0] + "\n" + output[1] + "\n" + output[2] + "\n" + output[3] + "\n" + output[4];
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        LoginMenuController.currentUser = currentUser;
    }
}
