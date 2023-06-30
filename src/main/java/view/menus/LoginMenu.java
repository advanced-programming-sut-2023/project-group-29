package view.menus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.Command;

import java.net.URL;
import java.util.Scanner;

public class LoginMenu extends Application {
    static Scanner scanner;
    private static Pane pane;

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

    public static Pane getPane() {
        return pane;
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = Command.class.getResource("/FXML/LoginMenu.fxml");
        Pane borderPane = FXMLLoader.load(url);
        pane = borderPane;
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }


}
