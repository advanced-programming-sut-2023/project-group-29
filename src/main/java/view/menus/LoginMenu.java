package view.menus;

import controller.MenuNames;
import controller.menucontrollers.LoginMenuController;
import model.AppData;
import view.Command;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu {
    static Scanner scanner;

    public static MenuNames run(Scanner scanner) {
        LoginMenu.scanner = scanner;
        System.out.println("You have entered login menu");
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if ((matcher = Command.getMatcher(input, Command.USER_CREATE)) != null) {
                System.out.println(LoginMenuController.createUser(matcher));
            }
            else if (Command.getMatcher(input, Command.EXIT) != null) {
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
                    try {
                        Thread.sleep(AppData.getDelayInLogin());

                    } catch (Exception e) {
                    }
                }
            }
            else if ((matcher = Command.getMatcher(input, Command.FORGET_PASSWORD)) != null) {
                System.out.println(LoginMenuController.forgottenPassword(matcher));
            }
            else {
                System.out.println("Invalid command!");
            }
        }
    }

    public static String checkSecurityQuestion() {
        String output;
        while (true) {
            System.out.println("Pick your security question: 1. What is my father’s name? 2. What " +
                    "was my first pet’s name? 3. What is my mother’s last name?");
            String input = scanner.nextLine();
            String regex1 = "question pick -q (?<questionNumber>\\d) -a (?<answer>\\S+) -c (?<answerconfirm>\\S+)";
            String regex2 = "question pick -a (?<answer>\\S+) -c (?<answerconfirm>\\S+) -q (?<questionNumber>\\d)";
            Matcher matcher1 = Pattern.compile(regex1).matcher(input);
            Matcher matcher2 = Pattern.compile(regex2).matcher(input);
            if (matcher1.matches()) {
                if (Integer.parseInt(matcher1.group(1)) > 3) {
                    System.out.println("Invalid number of question!");
                }
                else if (!matcher1.group(2).equals(matcher1.group(3))) {
                    System.out.println("Please enter your answer confirm correctly");
                }
                else {
                    output = matcher1.group(1) + " " + matcher1.group(2);
                    break;
                }
            }
            else if (matcher2.matches()) {
                if (Integer.parseInt(matcher2.group(3)) > 3) {
                    System.out.println("Invalid number of question!");
                }
                else if (!matcher2.group(1).equals(matcher2.group(2))) {
                    System.out.println("Please enter your answer confirm correctly");
                }
                else {
                    output = matcher2.group(3) + " " + matcher2.group(1);
                    break;
                }
            }
        }
        return output;
    }

    public static String suggestUsername(String username) {
        System.out.println("This username is already exist! Do you want to have " + username + "1 as your username?");
        return scanner.nextLine();

    }

    public static String askSecurityQuestion(int numberOfQuestion) {
        if (numberOfQuestion == 1) {
            System.out.println("What is your father’s name?");
        }
        else if (numberOfQuestion == 2) {
            System.out.println("What was your first pet’s name?");
        }
        else if (numberOfQuestion == 3) {
            System.out.println("What is your mother’s last name?");
        }
        return scanner.nextLine();
    }

    public static String changePasswordWithSecurityQuestion() {
        System.out.println("Enter your new password and its confirmation");
        return scanner.nextLine();
    }

    public static String showRandomPassword(String output) {
        System.out.println("Your random password is: " + output + ". Please re-enter your password here:");
        return scanner.nextLine();
    }

    public static String captcha(String[] mtCaptcha) {
        for (int i = 0; i < 5; i++) {
            System.out.println(mtCaptcha[i]);
        }
        return scanner.nextLine();
    }
}
