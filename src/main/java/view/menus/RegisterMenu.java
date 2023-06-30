package view.menus;

import controller.menucontrollers.LoginMenuController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.Command;

import java.net.URL;

public class RegisterMenu extends Application {
    private static Pane pane;
    private static TextField usernameTextField;
    private static Text usernameText;
    private static TextField passwordField;
    private static PasswordField passwordTextField;
    private static TextField emailTextField;
    private static TextField nicknameTextField;
    private static TextField sloganTextField;
    private static Text passwordText;
    private static Text emailText;
    private static Text nicknameText;
    private static Text sloganText;
    private static CheckBox sloganCheckBox;
    private static Button randomSlogan;
    private static Timeline timelineUsername;
    private static Timeline timelinePassword;

    private Button slogan1;
    private Button slogan2;
    private Button slogan3;

    public static Pane getPane() {
        return pane;
    }

    public static Timeline getTimelineUsername() {
        return timelineUsername;
    }

    public static Timeline getTimelinePassword() {
        return timelinePassword;
    }

    public static TextField getUsernameTextField() {
        return usernameTextField;
    }

    public static TextField getPasswordTextField() {
        return passwordTextField;
    }

    public static TextField getPasswordField() {
        return passwordField;
    }

    public static TextField getEmailTextField() {
        return emailTextField;
    }

    public static TextField getNicknameTextField() {
        return nicknameTextField;
    }

    public static Text getUsernameText() {
        return usernameText;
    }

    public static Text getPasswordText() {
        return passwordText;
    }

    public static Text getEmailText() {
        return emailText;
    }

    public static Text getNicknameText() {
        return nicknameText;
    }

    public static TextField getSloganTextField() {
        return sloganTextField;
    }

    public static Text getSloganText() {
        return sloganText;
    }

    public static CheckBox getSloganCheckBox() {
        return sloganCheckBox;
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = Command.class.getResource("/FXML/RegisterMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        RegisterMenu.pane = pane;
        mapping();
        checkingUserNameBox();
        checkingPasswordBox();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void checkingUserNameBox() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {
            initializedUsername();
        }));
        timelineUsername = timeline;
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void checkingPasswordBox() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> initializedPassword()));
        timelinePassword = timeline;
        timeline.setCycleCount(-1);
        timeline.play();
    }

    public void initializedUsername() {
        if (LoginMenuController.checkFormatOfUsername(usernameTextField.getText()) == 0) {
            usernameText.setText("Invalid Format");
        }
        else {
            usernameText.setText("");
        }

    }

    public void initializedPassword() {

        if (LoginMenuController.checkWeakPassword(passwordTextField.getText()) == 0 && passwordTextField.isVisible()) {
            passwordText.setText("Your password was short!");
        }

        else if (LoginMenuController.checkWeakPassword(passwordField.getText()) == 0 && passwordField.isVisible()) {
            passwordText.setText("Your password was short!");
        }

        else if (LoginMenuController.checkWeakPassword(passwordTextField.getText()) == 1 && passwordTextField.isVisible()) {
            passwordText.setText("Your password was week!");
        }

        else if (LoginMenuController.checkWeakPassword(passwordField.getText()) == 1 && passwordField.isVisible()) {
            passwordText.setText("Your password was week!");
        }

        else {
            passwordText.setText("");
        }

    }

    private void mapping() {
        TextField usernameTextField = new TextField();
        usernameTextField.setPromptText("Username");
        usernameTextField.setLayoutX(32);
        usernameTextField.setLayoutY(221);
        usernameTextField.setMaxWidth(200);
        usernameTextField.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                "-fx-background-insets: 50;" + "-fx-border-color: linear-gradient(#ffffff, #000000);" + "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: white");
        RegisterMenu.usernameTextField = usernameTextField;
        Text userText = new Text();
        userText.setLayoutX(32);
        userText.setLayoutY(258);
        userText.setFill(Color.RED);
        RegisterMenu.usernameText = userText;
        PasswordField passwordTextField = new PasswordField();
        passwordTextField.setPromptText("Password");
        passwordTextField.setLayoutX(32);
        passwordTextField.setLayoutY(287);
        passwordTextField.setMaxWidth(200);
        passwordTextField.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                "-fx-background-insets: 50;" + "-fx-border-color: linear-gradient(#ffffff, #000000);" + "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: white");
        RegisterMenu.passwordTextField = passwordTextField;
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");
        passwordField.setLayoutX(32);
        passwordField.setLayoutY(287);
        passwordField.setMaxWidth(200);
        passwordField.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                "-fx-background-insets: 50;" + "-fx-border-color: linear-gradient(#ffffff, #000000);" + "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: white");
        passwordField.setVisible(false);
        RegisterMenu.passwordField = passwordField;
        Text passwordText = new Text();
        passwordText.setLayoutX(32);
        passwordText.setLayoutY(324);
        passwordText.setFill(Color.RED);
        RegisterMenu.passwordText = passwordText;
        TextField emailTextField = new TextField();
        emailTextField.setPromptText("Email");
        emailTextField.setLayoutX(32);
        emailTextField.setLayoutY(353);
        emailTextField.setMaxWidth(200);
        emailTextField.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                "-fx-background-insets: 50;" + "-fx-border-color: linear-gradient(#ffffff, #000000);" + "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: white");
        RegisterMenu.emailTextField = emailTextField;
        Text emailText = new Text();
        emailText.setLayoutX(32);
        emailText.setLayoutY(390);
        emailText.setFill(Color.RED);
        RegisterMenu.emailText = emailText;
        TextField nicknameTextField = new TextField();
        nicknameTextField.setPromptText("Nickname");
        nicknameTextField.setLayoutX(32);
        nicknameTextField.setLayoutY(418);
        nicknameTextField.setMaxWidth(200);
        nicknameTextField.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                "-fx-background-insets: 50;" + "-fx-border-color: linear-gradient(#ffffff, #000000);" + "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: white");
        RegisterMenu.nicknameTextField = nicknameTextField;
        Text nicknameText = new Text();
        nicknameText.setLayoutX(32);
        nicknameText.setLayoutY(455);
        nicknameText.setFill(Color.RED);
        RegisterMenu.nicknameText = nicknameText;
        TextField sloganTextField = new TextField();
        sloganTextField.setPromptText("Slogan");
        sloganTextField.setLayoutX(32);
        sloganTextField.setLayoutY(481);
        sloganTextField.setMinWidth(250);
        sloganTextField.setVisible(false);
        sloganTextField.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                "-fx-background-insets: 50;" + "-fx-border-color: linear-gradient(#ffffff, #000000);" + "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: white");
        RegisterMenu.sloganTextField = sloganTextField;
        Text sloganText = new Text();
        sloganText.setLayoutX(32);
        sloganText.setLayoutY(518);
        sloganText.setFill(Color.RED);
        RegisterMenu.sloganText = sloganText;
        CheckBox sloganCheckBox = new CheckBox();
        sloganCheckBox.setText("Slogan");
        sloganCheckBox.setLayoutX(32);
        sloganCheckBox.setLayoutY(544);
        sloganCheckBox.setStyle("-fx-effect: dropshadow(gaussian, blue, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: white;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 13px;");
        sloganCheckBox.setTextFill(Color.WHITE);
        RegisterMenu.sloganCheckBox = sloganCheckBox;
        Button randomSlogan = new Button();
        randomSlogan.setStyle("-fx-effect: dropshadow(gaussian, red, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: black;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 13px;");
        randomSlogan.setLayoutX(32);
        randomSlogan.setLayoutY(512);
        randomSlogan.setText("Random");
        randomSlogan.setVisible(false);
        RegisterMenu.randomSlogan = randomSlogan;
        Button slogan1 = new Button();
        Button slogan2 = new Button();
        Button slogan3 = new Button();
        slogan1.setText("This day, is a very bad day for our enemies!");
        slogan2.setText("Our empire destroys its enemies!");
        slogan3.setText("You are just a looser!");
        slogan1.setLayoutX(300);
        slogan2.setLayoutX(300);
        slogan3.setLayoutX(300);
        slogan1.setLayoutY(480);
        slogan2.setLayoutY(530);
        slogan3.setLayoutY(580);
        slogan1.setVisible(false);
        slogan2.setVisible(false);
        slogan3.setVisible(false);
        this.slogan1 = slogan1;
        this.slogan2 = slogan2;
        this.slogan3 = slogan3;

        slogan1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                sloganTextField.setText(slogan1.getText());
            }
        });

        slogan2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                sloganTextField.setText(slogan2.getText());
            }
        });

        slogan3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                sloganTextField.setText(slogan3.getText());
            }
        });


        pane.getChildren().add(slogan1);
        pane.getChildren().add(slogan2);
        pane.getChildren().add(slogan3);
        pane.getChildren().add(usernameTextField);
        pane.getChildren().add(userText);
        pane.getChildren().add(passwordTextField);
        pane.getChildren().add(passwordField);
        pane.getChildren().add(passwordText);
        pane.getChildren().add(emailTextField);
        pane.getChildren().add(nicknameTextField);
        pane.getChildren().add(emailText);
        pane.getChildren().add(nicknameText);
        pane.getChildren().add(sloganTextField);
        pane.getChildren().add(sloganText);
        pane.getChildren().add(sloganCheckBox);
        pane.getChildren().add(randomSlogan);
        sloganCheckBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                showSloganPart(mouseEvent);
            }
        });
        randomSlogan.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                createRandomSlogan();
            }
        });
    }

    public void showSloganPart(MouseEvent mouseEvent) {
        CheckBox checkBox = (CheckBox) mouseEvent.getSource();
        if (checkBox.isSelected()) {
            sloganTextField.setVisible(true);
            randomSlogan.setVisible(true);
            slogan1.setVisible(true);
            slogan2.setVisible(true);
            slogan3.setVisible(true);
        }
        else {
            sloganTextField.setVisible(false);
            randomSlogan.setVisible(false);
            slogan1.setVisible(false);
            slogan2.setVisible(false);
            slogan3.setVisible(false);
            sloganText.setText("");
            sloganTextField.setText("");
        }
    }

    public void createRandomSlogan() {
        RegisterMenu.getSloganTextField().setText(LoginMenuController.createRandomSlogan());
    }
}