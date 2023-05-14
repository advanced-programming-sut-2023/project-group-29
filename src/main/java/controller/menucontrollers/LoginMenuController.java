package controller.menucontrollers;

import model.AppData;
import model.SaveAndLoad;
import model.User;
import view.menus.LoginMenu;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenuController {

    public static int checkWeakPassword(String password) {
        Matcher matcherCapital = Pattern.compile("[A-Z]").matcher(password);
        Matcher matcherSmall = Pattern.compile("[a-z]").matcher(password);
        Matcher matcherNumber = Pattern.compile("\\d").matcher(password);
        Matcher matcherOdd = Pattern.compile("[!@#&%_\\-=~+()^{}\"?]").matcher(password);
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

        String password = LoginMenu.showRandomPassword(output);
        if (!password.equals(output)) {
            return "Incorrect password!";
        }
        return output;
    }

    private static String addCharToPassword(Random random, String output, int n1, int n2) {
        int myRandom = random.nextInt() % n1;
        myRandom *= myRandom;
        int charAscii = (myRandom % n1) + n2;
        return output + (char) charAscii;
    }

    private static String createRandomSlogan() {
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

    public static String createUser(Matcher matcher) {
        String input = matcher.group(0);
        if (checkInvalidCommand(input) == 1) return "Invalid command!";
        Matcher matcherUsername = Pattern.compile
                ("-u\\s+(?<username>\\w[^-&^\\s]+\\S)").matcher(input);
        Matcher matcherPassword = Pattern.compile("-p\\s+(?<password>\\S[^-&^\\s]+\\S)\\s+" +
                "(?<passwordConfirmation>\\S[^-&^\\s]+\\S+)?").matcher(input);
        Matcher matcherNickname = Pattern.compile("-n\\s+(?<nickname>\\w[^-&^\\s]+\\S)").matcher(input);
        Matcher matcherEmail = Pattern.compile("-email\\s+(?<email>\\w[^-&^\\s]+\\S)").matcher(input);
        Matcher matcherExistSlogan = Pattern.compile(".+-s.+").matcher(input);
        Matcher matcherSlogan = Pattern.compile("-s\\s+(?<slogan>\\w[^-]+\\w)").matcher(input);
        int checkFilled = checkEmptyFilled
                (matcherExistSlogan, matcherUsername, matcherPassword, matcherNickname, matcherEmail, matcherSlogan);
        if (checkFilled == 1) return "Fill your username";
        if (checkFilled == 2) return "Fill your password";
        if (checkFilled == 3) return "Fill your nickname";
        if (checkFilled == 4) return "Fill your email";
        if (checkFilled == 5) return "Fill your slogan";
        String username = matcherUsername.group(1);
        Matcher matcherCheckUsername = Pattern.compile("\\w+").matcher(username);
        if (!matcherCheckUsername.matches()) {
            return "Check format of your username";
        }
        if (AppData.getUserByUsername(username) != null) {
            String newInput = LoginMenu.suggestUsername(username);
            if (!(newInput.equals("Yes") || newInput.equals("yes"))) {
                return "Account didn't create!";
            }
            username = username + "1";
            if (AppData.getUserByUsername(username) != null) {
                return "This Username is already exist too!\nPlease try creating user again!";
            }
        }
        String password = matcherPassword.group(1);
        if (!password.equals("random")) {
            int checkPass = checkWeakPassword(password);
            if (checkPass == 0) {
                return "Your password is short!";
            }
            else if (checkPass == 1) {
                return "Your password has a incorrect format!";
            }
            String passwordConfirmation = matcherPassword.group(2);
            if (!password.equals(passwordConfirmation)) {
                return "Please enter password confirmation correctly!";
            }
        }
        else {
            password = createRandomPassword();
            if (password.equals("Incorrect password!")) {
                return "Incorrect password!";
            }
        }
        if (AppData.getUserByEmail(matcherEmail.group(1)) != null) {
            return "This email is already exist!";
        }
        if (checkEmailFormat(matcherEmail.group(1)) == 0) {
            return "Check format of your email";
        }
        String hashPassword = SaveAndLoad.hashString(password);
        String securityQuestion = LoginMenu.checkSecurityQuestion();
        if (!captcha()) {
            return "Your register doesn't complete!";
        }
        User user = new User(username, hashPassword, matcherNickname.group(1), matcherEmail.group(1),
                getSlogan(matcherExistSlogan, matcherSlogan), securityQuestion);
        AppData.addUser(user);
        SaveAndLoad.saveData(AppData.getUsers(), AppData.getUsersDataBaseFilePath());
        return "user created successfully";
    }

    public static String login(Matcher matcher) {
        String input = matcher.group(0);
        Pattern patternPassword = Pattern.compile("\\s*-p\\s+(\\S+)\\s*");
        Matcher matcherUsername = Pattern.compile("\\s*-u\\s+(\\S+)\\s*").matcher(input);
        Matcher matcherPassword = patternPassword.matcher(input);
        if (!(matcherUsername.find() && matcherPassword.find())) {
            return "Invalid command!";
        }
        String username = matcherUsername.group(1);
        String password = matcherPassword.group(1);
        if (AppData.getUserByUsername(username) == null) {
            return "User with this username doesn't exist!";
        }
        else if (!AppData.getUserByUsername(username).getPassword().equals(SaveAndLoad.hashString(password))) {
            return "Username and password didn't match!";
        }
        if (!captcha()) {
            return "You can't logged in!";
        }
        Matcher matcherStayLoggedIn = Pattern.compile("--stay-logged-in").matcher(input);
        if (matcherStayLoggedIn.find()) {
            AppData.getUserByUsername(username).setStayLoggedIn(1);
        }
        AppData.setCurrentUser(AppData.getUserByUsername(username));
        SaveAndLoad.saveData(AppData.getUsers(), AppData.getUsersDataBaseFilePath());
        return "user logged in successfully!";
    }

    public static String forgottenPassword(Matcher matcher) {
        String username = matcher.group(1);
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

    public static Boolean captcha() {
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
        String answer = LoginMenu.captcha(output);
        Matcher answerMatcher = Pattern.compile("\\d+").matcher(answer);
        if (!answerMatcher.matches()) {
            return false;
        }
        return Integer.parseInt(answer) == result;
    }
}
