package view.menus;

import controller.menucontrollers.LoginMenuController;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.AppData;
import model.SaveAndLoad;

public class LoginMenuGraphic {
    public TextField username;
    public PasswordField password;

    public void back(MouseEvent mouseEvent) throws Exception {
        new EnterMenu().start(AppData.getStage());
    }

    public void forgotPassword(MouseEvent mouseEvent) {
        if (AppData.getUserByUsername(username.getText()) == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong Username");
            alert.setContentText("User with this username doesn't exist!");
            alert.showAndWait();
            return;
        }
        String[] securityQuestion = AppData.getUserByUsername(username.getText()).getSecurityQuestion().split(" ");
        int numberOfQuestion = Integer.parseInt(securityQuestion[0]);
        String answer = securityQuestion[1];
        for (int i = 0; i < LoginMenu.getPane().getChildren().size(); i++) {
            if (LoginMenu.getPane().getChildren().get(i).getClass().getSimpleName().equals("Text")) {
                return;
            }
        }
        Text text = new Text();
        if (numberOfQuestion == 1) {
            text.setText("What is your father’s name?");
        }
        else if (numberOfQuestion == 2) {
            text.setText("What was your first pet’s name?");
        }
        else {
            text.setText("What is your mother’s last name?");
        }
        text.setLayoutY(503);
        text.setLayoutX(650);
        text.setFill(Color.WHITE);
        text.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                "-fx-background-insets: 50;" + "-fx-border-color: linear-gradient(#ffffff, #000000);" + "-fx-text-fill: white;" +
                "-fx-font-size: 15px");
        LoginMenu.getPane().getChildren().add(text);
        TextField textField = new TextField();
        textField.setPromptText("Answer");
        textField.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                "-fx-background-insets: 50;" + "-fx-border-color: linear-gradient(#ffffff, #000000);" + "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: white");
        textField.setLayoutY(485);
        textField.setLayoutX(900);
        LoginMenu.getPane().getChildren().add(textField);
        Button button = new Button();
        button.setText("Confirm");
        button.setLayoutX(890);
        button.setLayoutY(515);
        button.setStyle("-fx-effect: dropshadow(gaussian, red, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: black;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 20px;");
        LoginMenu.getPane().getChildren().add(button);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (textField.getText().equals(answer)) {
                    button.setVisible(false);
                    PasswordField textField1 = new PasswordField();
                    textField1.setPromptText("New Password");
                    textField1.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                            "-fx-background-insets: 50;" + "-fx-border-color: linear-gradient(#ffffff, #000000);" + "-fx-text-fill: white;" +
                            "-fx-prompt-text-fill: white");
                    textField1.setLayoutY(520);
                    textField1.setLayoutX(900);
                    LoginMenu.getPane().getChildren().add(textField1);
                    PasswordField textField2 = new PasswordField();
                    textField2.setPromptText("Confirm New Password");
                    textField2.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                            "-fx-background-insets: 50;" + "-fx-border-color: linear-gradient(#ffffff, #000000);" + "-fx-text-fill: white;" +
                            "-fx-prompt-text-fill: white");
                    textField2.setLayoutY(555);
                    textField2.setLayoutX(900);
                    LoginMenu.getPane().getChildren().add(textField2);
                    Button changePass = new Button();
                    changePass.setText("Confirm");
                    changePass.setLayoutX(890);
                    changePass.setLayoutY(590);
                    changePass.setStyle("-fx-effect: dropshadow(gaussian, red, 10, 0, 0, 0);" +
                            "    -fx-background-insets: 50;" +
                            "    -fx-text-fill: black;" +
                            "    -fx-font-family: \"Brush Script MT\";" +
                            "    -fx-font-size: 20px;");
                    LoginMenu.getPane().getChildren().add(changePass);
                    changePass.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if (textField1.getText().equals(textField2.getText())) {
                                if (LoginMenuController.checkWeakPassword(textField1.getText()) != 2) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Week Password");
                                    alert.setContentText("Your new password was week!");
                                    alert.showAndWait();
                                    return;
                                }
                                AppData.getUserByUsername(username.getText()).setPassword(SaveAndLoad.hashString(textField1.getText()));
                                SaveAndLoad.saveData(AppData.getUsers(), AppData.getUsersDataBaseFilePath());
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Successful change");
                                alert.setContentText("Your password change successfully");
                                alert.showAndWait();
                                LoginMenu.getPane().getChildren().remove(textField1);
                                LoginMenu.getPane().getChildren().remove(textField2);
                                LoginMenu.getPane().getChildren().remove(textField);
                                LoginMenu.getPane().getChildren().remove(changePass);
                                LoginMenu.getPane().getChildren().remove(text);
                            }
                            else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Wrong Confirmation");
                                alert.setContentText("Your new password and its confirmation weren't match with each other!");
                                alert.showAndWait();
                            }
                        }
                    });
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Wrong Answer");
                    alert.setContentText("Your answer was wrong!");
                    alert.showAndWait();
                }
            }
        });
    }

    public void login() throws Exception {
        String output = LoginMenuController.login(username.getText(), password.getText());
        if (output.equals("User with this username doesn't exist!")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong Username");
            alert.setContentText("User with this username doesn't exist!");
            alert.showAndWait();
        }
        else if (output.equals("Username and password didn't match!")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong Password");
            alert.setContentText("Username and password didn't match!");
            alert.showAndWait();
            AppData.setDelayInLogin(AppData.getDelayInLogin() + 5000);
            try {
                Thread.sleep(AppData.getDelayInLogin());

            } catch (Exception e) {
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successfully");
            alert.setContentText("You entered main menu");
            alert.showAndWait();
            new MainMenu().start(AppData.getStage());
        }
    }


}
