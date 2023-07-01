package view.menus;

import controller.menucontrollers.LoginMenuController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.AppData;
import model.SaveAndLoad;
import view.Command;

import java.net.URL;

public class SecurityQuestionMenu extends Application {
    Pane pane;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    TextField answerTextField;
    Captcha captcha;
    Button confirm;
    TextField captchaText;

    @Override
    public void start(Stage stage) throws Exception {
        URL url = Command.class.getResource("/FXML/SecurityQuestionMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        this.pane = pane;
        mapping();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void mapping() {
        CheckBox checkBox1 = new CheckBox();
        CheckBox checkBox2 = new CheckBox();
        CheckBox checkBox3 = new CheckBox();
        checkBox1.setLayoutX(110);
        checkBox2.setLayoutX(410);
        checkBox3.setLayoutX(710);
        checkBox1.setLayoutY(200);
        checkBox2.setLayoutY(200);
        checkBox3.setLayoutY(200);
        checkBox1.setText("What is my father’s name?");
        checkBox2.setText("What was my first pet’s name?");
        checkBox3.setText("What is my mother’s last name?");
        checkBox1.setStyle("-fx-effect: dropshadow(gaussian, blue, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: white;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 23px;");
        checkBox2.setStyle("-fx-effect: dropshadow(gaussian, blue, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: white;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 23px;");
        checkBox3.setStyle("-fx-effect: dropshadow(gaussian, blue, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: white;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 23px;");
        this.checkBox1 = checkBox1;
        this.checkBox2 = checkBox2;
        this.checkBox3 = checkBox3;
        TextField textField = new TextField();
        textField.setPromptText("Answer");
        textField.setLayoutY(300);
        textField.setLayoutX(465);
        textField.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                "-fx-background-insets: 50;" +
                "-fx-border-color: linear-gradient(#ffffff, #000000);" +
                "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: white");
        textField.setVisible(false);
        this.answerTextField = textField;
        Button confirm = new Button();
        confirm.setText("Confirm");
        confirm.setLayoutY(500);
        confirm.setLayoutX(445);
        confirm.prefHeight(61);
        confirm.prefWidth(149);
        confirm.setStyle("-fx-effect: dropshadow(gaussian, red, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: black;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 40px;");
        this.confirm = confirm;
        makeCaptcha();
        pane.getChildren().add(textField);
        pane.getChildren().add(checkBox1);
        pane.getChildren().add(checkBox2);
        pane.getChildren().add(checkBox3);
        pane.getChildren().add(confirm);
        checkBox1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CheckBox checkBox = (CheckBox) mouseEvent.getSource();
                if (checkBox.isSelected()) {
                    textField.setVisible(true);
                    checkBox2.setSelected(false);
                    checkBox3.setSelected(false);
                }
            }
        });

        checkBox2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CheckBox checkBox = (CheckBox) mouseEvent.getSource();
                if (checkBox.isSelected()) {
                    textField.setVisible(true);
                    checkBox1.setSelected(false);
                    checkBox3.setSelected(false);
                }
            }
        });

        checkBox3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CheckBox checkBox = (CheckBox) mouseEvent.getSource();
                if (checkBox.isSelected()) {
                    textField.setVisible(true);
                    checkBox1.setSelected(false);
                    checkBox2.setSelected(false);
                }
            }
        });
        confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!(checkBox1.isSelected() || checkBox2.isSelected() || checkBox3.isSelected())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Empty Choice");
                    alert.setContentText("Please check one of boxes!");
                    alert.showAndWait();
                    return;
                }
                String correctNumber = String.valueOf(captcha.getNumber());
                if (!captchaText.getText().equals(correctNumber)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("False captcha");
                    alert.setContentText("Please enter correct captcha");
                    alert.showAndWait();
                    return;
                }
                if (checkBox1.isSelected()) {
                    LoginMenuController.getCurrentUser().setSecurityQuestion("1 " + answerTextField.getText());
                }
                else if (checkBox2.isSelected()) {
                    LoginMenuController.getCurrentUser().setSecurityQuestion("2 " + answerTextField.getText());
                }
                else if (checkBox3.isSelected()) {
                    LoginMenuController.getCurrentUser().setSecurityQuestion("3 " + answerTextField.getText());
                }
                AppData.updateUserData(LoginMenuController.getCurrentUser());

                LoginMenuController.setCurrentUser(null);
                try {
                    new EnterMenu().start(AppData.getStage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }

    private void makeCaptcha() {
        makeCaptchaImage();
        makeCaptchaText();
    }

    private void makeCaptchaText() {
        captchaText = new TextField();
        captchaText.setPromptText("captcha");
        captchaText.setTranslateX(465);
        captchaText.setTranslateY(370);
        captchaText.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                "-fx-background-insets: 50;" +
                "-fx-border-color: linear-gradient(#ffffff, #000000);" +
                "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: white");
        pane.getChildren().add(captchaText);
    }

    private void makeCaptchaImage() {
        int imageNumber = (int) (Math.random() * 50) + 1;
        captcha = Captcha.getCaptchaByNumber(imageNumber);
        Image image = new Image(SecurityQuestionMenu.class.getResource(captcha.getImageAddress()).toString());
        ImageView imageView = new ImageView(image);
        imageView.setX(460);
        imageView.setY(430);
        pane.getChildren().add(imageView);
    }
}
