package controller;

import model.AppData;
import model.User;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenuController {

    public static int checkWeakPassword(String password) {
        //TODO: replace with regex!!
        int checkLengthOfPassword = 0;
        int checkCapital = 0;
        int checkSmall = 0;
        int checkNumber = 0;
        int checkOddCharacter = 0;
        if (password.length() >= 6) {
            checkLengthOfPassword = 1;
        }
        for (int i = 0; i < password.length(); i++) {
            int passwordChar = (int) password.charAt(i);
            if (passwordChar >= 65 && passwordChar <= 90) {
                checkCapital = 1;
            } else if (passwordChar >= 97 && passwordChar <= 122) {
                checkSmall = 1;
            } else if (passwordChar >= 48 && passwordChar <= 57) {
                checkNumber = 1;
            } else {
                checkOddCharacter = 1;
            }
        }
        if (checkLengthOfPassword == 0) {
            return 0;
        }
        if (!(checkCapital == 1 && checkSmall == 1 && checkNumber == 1 && checkOddCharacter == 1)) {
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
        output = addCharToPassword(random, output, 26, 65);
        output = addCharToPassword(random, output, 26, 65);
        output = addCharToPassword(random, output, 26, 65);
        output = addCharToPassword(random, output, 26, 97);
        output = addCharToPassword(random, output, 10, 48);
        output = addCharToPassword(random, output, 10, 48);
        output = addCharToPassword(random, output, 10, 48);
        output = addCharToPassword(random, output, 15, 33);
        output = "Your random password is: " + output + "Please re-enter your password here:";
        System.out.println(output);
        Scanner scanner = new Scanner(System.in);
        String password = scanner.nextLine();
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

    private static String checkSecurityQuestion() {
        String output = "";
        while (true) {
            System.out.println("Pick your security question: 1. What is my father’s name? 2. What " +
                    "was my first pet’s name? 3. What is my mother’s last name?");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String regex1 = "question pick -q (?<questionNumber>\\d) -a (?<answer>\\S+) -c (?<answerconfirm>\\S+)";
            String regex2 = "question pick -a (?<answer>\\S+) -c (?<answerconfirm>\\S+) -q (?<questionNumber>\\d)";
            Matcher matcher1 = Pattern.compile(regex1).matcher(input);
            Matcher matcher2 = Pattern.compile(regex2).matcher(input);
            if (matcher1.matches()) {
                if (Integer.parseInt(matcher1.group(1)) > 3) {
                    System.out.println("Invalid number of question!");
                } else if (!matcher1.group(2).equals(matcher1.group(3))) {
                    System.out.println("Please enter your answer confirm correctly");
                } else {
                    output = matcher1.group(1) + " " + matcher1.group(2);
                    break;
                }
            } else if (matcher2.matches()) {
                if (Integer.parseInt(matcher2.group(3)) > 3) {
                    System.out.println("Invalid number of question!");
                } else if (!matcher2.group(1).equals(matcher2.group(2))) {
                    System.out.println("Please enter your answer confirm correctly");
                } else {
                    output = matcher2.group(3) + " " + matcher2.group(1);
                    break;
                }
            }
        }
        return output;
    }

    public static String createUser(Matcher matcher) {
        //TODO : split it to smaller functions
        String input = matcher.group(0);
        Pattern patternExistUsername = Pattern.compile("-u");
        Pattern patternUsername = Pattern.compile("-u (?<username>\\w\\S+)");
        Pattern patternExistPassword = Pattern.compile("-p");
        Pattern patternPassword = Pattern.compile("-p (?<password>\\w\\S+) (?<passwordConfirmation>\\w\\S+)?");
        Pattern patternExistNickname = Pattern.compile("-n");
        Pattern patternNickname = Pattern.compile("-n (?<nickname>\\w\\S+)");
        Pattern patternExistEmail = Pattern.compile("-email");
        Pattern patternEmail = Pattern.compile("-email (?<email>\\w\\S+)");
        Pattern patternExistSlogan = Pattern.compile(".+-s.+");
        Pattern patternSlogan = Pattern.compile("-s (?<slogan>\\w.+)");
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
            System.out.println("This username is already exist! Do you want to have " + username + "1 as your username?");
            Scanner scanner = new Scanner(System.in);
            String newInput = scanner.nextLine();
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
        String newSlogan = "";
        if (matcherExistSlogan.matches()) {
            if (!matcherSlogan.group(1).equals("random")) {
                slogan = matcherSlogan.group(1);
                for (int i = 0; i < slogan.length() - 1; i++) {
                    newSlogan += slogan.charAt(i);
                }
                slogan = newSlogan;
            } else {
                slogan = createRandomSlogan();
            }
        }
        User user = new User(username, password, nickname, email, slogan, checkSecurityQuestion());
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
        //TODO: it can be smaller
        String username = matcher.group(1);
        if (AppData.getUserByUsername(username) == null) {
            return "User with this username doesn't exist!";
        }
        String[] securityQuestion = AppData.getUserByUsername(username).getSecurityQuestion().split(" ");
        int numberOfQuestion = Integer.parseInt(securityQuestion[0]);
        String answer = securityQuestion[1];
        if (numberOfQuestion == 1) {
            System.out.println("What is your father’s name?");
        } else if (numberOfQuestion == 2) {
            System.out.println("What was your first pet’s name?");
        } else if (numberOfQuestion == 3) {
            System.out.println("What is your mother’s last name?");
        }
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.equals(answer)) {
            System.out.println("Enter your new password and its confirmation");
            Scanner scannerNewPassword = new Scanner(System.in);
            String newPassword = scannerNewPassword.nextLine();
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
