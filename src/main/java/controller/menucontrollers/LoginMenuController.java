package controller.menucontrollers;

import model.AppData;
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
        Pattern patternOfFormatOfEmail = Pattern.compile("\\S+@\\S+.\\S+");
        Matcher matcherOfFormatOfEmail = patternOfFormatOfEmail.matcher(email);
        if (matcherOfFormatOfEmail.matches()) {
            return 1;
        }
        return 0;
    }

    private static String createRandomPassword(String username) {
        String output = "";
        Random random = new Random();
        int charAscii = 0;
        int myRandom = 0;
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
        } else if (myRandom == 1) {
            return "Our empire destroys its enemies!";
        } else if (myRandom == 2) {
            return "You are just a looser!";
        } else if (myRandom == 3) {
            return "We are winner!";
        }
        return "Us against whole of the world!";
    }
    public static String createUser(Matcher matcher) {
        //TODO faratin: split it to smaller functions
        String input = matcher.group(0);
        Pattern patternExistUsername = Pattern.compile("-u");
        Pattern patternUsername = Pattern.compile("-u\\s+(?<username>\\w\\S+)");
        Pattern patternExistPassword = Pattern.compile("-p");
        Pattern patternPassword = Pattern.compile("-p\\s+(?<password>\\w\\S+)\\s+(?<passwordConfirmation>\\w\\S+)?");
        //TODO: faratin it was not good @ at the beginning of password
        Pattern patternExistNickname = Pattern.compile("-n");
        Pattern patternNickname = Pattern.compile("-n\\s+(?<nickname>\\w\\S+)");
        Pattern patternExistEmail = Pattern.compile("-email");
        Pattern patternEmail = Pattern.compile("-email\\s+(?<email>\\w\\S+)");
        Pattern patternExistSlogan = Pattern.compile(".+-s.+");
        Pattern patternSlogan = Pattern.compile("-s\\s+(?<slogan>\\w[^-]+\\w)");

        Matcher matcherExistUsername = patternExistUsername.matcher(input);
        Matcher matcherExistPassword = patternExistPassword.matcher(input);
        Matcher matcherExistNickname = patternExistNickname.matcher(input);
        Matcher matcherExistEmail = patternExistEmail.matcher(input);
        if (!(matcherExistUsername.find() && matcherExistPassword.find() &&
                matcherExistNickname.find() && matcherExistEmail.find())) {
            return "Invalid command!";
        }
        Matcher matcherUsername = patternUsername.matcher(input);
        if (!matcherUsername.find()) {
            return "Fill your username";
        }
        Matcher matcherPassword = patternPassword.matcher(input);
        if (!matcherPassword.find()) {
            return "Fill your password";
        }
        Matcher matcherNickname = patternNickname.matcher(input);
        if (!matcherNickname.find()) {
            return "Fill your nickname";
        }
        Matcher matcherEmail = patternEmail.matcher(input);
        if (!matcherEmail.find()) {
            return "Fill your email";
        }
        Matcher matcherExistSlogan = patternExistSlogan.matcher(input);
        Matcher matcherSlogan = patternSlogan.matcher(input);
        if (matcherExistSlogan.find() && !matcherSlogan.find()) {
            return "Fill your slogan";
        }
        String username = matcherUsername.group(1);
        Pattern patternCheckUsername = Pattern.compile("\\w+");
        Matcher matcherCheckUsername = patternCheckUsername.matcher(username);
        if (!matcherCheckUsername.matches()) {
            return "Check format of your username";
        }

        if (AppData.getUserByUsername(username) != null) {
            String newInput = LoginMenu.suggestUsername(username);
            if (!(newInput.equals("Yes") || newInput.equals("yes"))) {
                return "Account didn't create!";
            }
            username = username + "1";
        }
        String password = matcherPassword.group(1);
        if (!password.equals("random")) {
            int checkPass = checkWeakPassword(password);
            if (checkPass == 0) {
                return "Your password is short!";
            } else if (checkPass == 1) {
                return "Your password has a incorrect format!";
            }
            String passwordConfirmation = matcherPassword.group(2);
            if (!password.equals(passwordConfirmation)) {
                return "Please enter password confirmation correctly!";
            }
        } else {
            password = createRandomPassword(username);
            if (password.equals("Incorrect password!")) {
                return "Incorrect password!";
            }
        }
        String email = matcherEmail.group(1);
        if (AppData.getUserByEmail(email) != null) {
            return "This email is already exist!";
        }
        if (checkEmailFormat(email) == 0) {
            return "Check format of your email";
        }
        String nickname = matcherNickname.group(1);
        String slogan = "";
        if (matcherExistSlogan.matches()) {
            if (!matcherSlogan.group(1).equals("random")) {
                slogan = matcherSlogan.group(1);
            } else {
                slogan = createRandomSlogan();
            }
        }
        User user = new User(username, password, nickname, email, slogan, LoginMenu.checkSecurityQuestion());
        AppData.addUser(user);
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
        } else if (!AppData.getUserByUsername(username).getPassword().equals(password)) {
            return "Username and password didn't match!";
        }
        Matcher matcherStayLoggedIn = Pattern.compile("--stay-logged-in").matcher(input);
        if (matcherStayLoggedIn.find()) {
            AppData.setStayLoggedIn(1);
        }
        AppData.setCurrentUser(AppData.getUserByUsername(username));
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
            AppData.getUserByUsername(username).setPassword(password);
            return "Password changed successfully";
        }

        return "Wrong answer!";
    }
}
