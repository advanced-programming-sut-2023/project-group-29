package view.menus;

import controller.menucontrollers.LoginMenuController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.Command;

import java.net.URL;

public class SecurityQuestionMenu extends Application {
    Pane pane;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    TextField answerTextField;
    Text captcha;
    Button confirm;
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
        confirm.setLayoutY(370);
        confirm.setLayoutX(460);
        confirm.prefHeight(61);
        confirm.prefWidth(149);
        confirm.setStyle("-fx-effect: dropshadow(gaussian, red, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: black;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 40px;");
        this.confirm = confirm;
        Text captcha = new Text();
        captcha.setText(LoginMenuController.captcha());
        captcha.setLayoutY(500);
        captcha.setLayoutX(30);
        captcha.setStyle("-fx-text-fill: white;" + "-fx-font-size: 10px");
        captcha.setFill(Color.WHITE);
        captcha.setVisible(false);
        this.captcha = captcha;
        pane.getChildren().add(textField);
        pane.getChildren().add(checkBox1);
        pane.getChildren().add(checkBox2);
        pane.getChildren().add(checkBox3);
        pane.getChildren().add(confirm);
        pane.getChildren().add(captcha);
        checkBox1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CheckBox checkBox = (CheckBox) mouseEvent.getSource();
                if(checkBox.isSelected()) {
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
                if(checkBox.isSelected()) {
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
                if(checkBox.isSelected()) {
                    textField.setVisible(true);
                    checkBox1.setSelected(false);
                    checkBox2.setSelected(false);
                }
            }
        });
        confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!(checkBox1.isSelected() || checkBox2.isSelected() || checkBox3.isSelected())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Empty Choice");
                    alert.setContentText("Please check one of boxes!");
                    alert.showAndWait();
                    return;
                }
                captcha.setVisible(true);
            }
        });
    }
}
