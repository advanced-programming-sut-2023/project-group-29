package view.menus;

import controller.menucontrollers.LoginMenuController;
import controller.menucontrollers.ProfileMenuController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.AppData;
import model.Avatar;
import model.LeaderBoard;
import model.SaveAndLoad;
import view.Command;

import java.io.File;
import java.net.URL;


public class ProfileMenu extends Application {
    private Pane pane;
    private Text descriptionText;
    private TextField usernameTextfield;
    private TextField nicknameTextField;
    private TextField sloganTextField;
    private TextField emailTextField;
    private Text usernameText;
    private Timeline timelineUsername;
    private Dialog changePassDialog;
    private Timeline timelinePassword;
    private Text passwordText;
    private TextField newPasswordTextField;
    private TextField oldPasswordTextField;
    private Avatar avatar;

    @Override
    public void start(Stage stage) throws Exception {
        URL url = Command.class.getResource("/FXML/ProfileMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        this.pane = pane;
        initializeButton();
        initializeTextField();
        checkingUserNameBox();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void initializeButton() {
        String styleOfButton = "-fx-effect: dropshadow(gaussian, red, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: black;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 30px;";
        Text descriptionText = new Text();
        String slogan = AppData.getCurrentUser().getSlogan();
        if (slogan.length() < 1) {
            slogan = "Slogan is empty";
        }
        String textOfDescription = AppData.getCurrentUser().username + "\n" + AppData.getCurrentUser().password + "\n" +
                AppData.getCurrentUser().nickname + "\n" + AppData.getCurrentUser().email + "\n" + slogan;
        descriptionText.setText(textOfDescription);
        descriptionText.setLayoutX(10);
        descriptionText.setLayoutY(15);
        descriptionText.setStyle("-fx-effect: dropshadow(gaussian, white, 10, 0, 0, 0);");
        Avatar avatar = new Avatar();
        this.avatar = avatar;
        Button changePassButton = new Button();
        changePassButton.setLayoutX(430);
        changePassButton.setLayoutY(250);
        changePassButton.setStyle(styleOfButton);
        changePassButton.setText("Change Password");
        Button changeUsernameButton = new Button();
        changeUsernameButton.setLayoutX(540);
        changeUsernameButton.setLayoutY(150);
        changeUsernameButton.setStyle(styleOfButton);
        changeUsernameButton.setText("Change Username");
        Button changeNicknameButton = new Button();
        changeNicknameButton.setLayoutX(540);
        changeNicknameButton.setLayoutY(350);
        changeNicknameButton.setStyle(styleOfButton);
        changeNicknameButton.setText("Change Nickname");
        Button changeEmainButton = new Button();
        changeEmainButton.setLayoutX(540);
        changeEmainButton.setLayoutY(450);
        changeEmainButton.setStyle(styleOfButton);
        changeEmainButton.setText("Change Email");
        Button changeSloganButton = new Button();
        changeSloganButton.setLayoutX(540);
        changeSloganButton.setLayoutY(520);
        changeSloganButton.setStyle(styleOfButton);
        changeSloganButton.setText("Change Slogan");
        Button removeSloganButton = new Button();
        removeSloganButton.setLayoutX(540);
        removeSloganButton.setLayoutY(580);
        removeSloganButton.setStyle(styleOfButton);
        removeSloganButton.setText("Remove Slogan");
        Button scoreBoard = new Button();
        scoreBoard.setLayoutX(900);
        scoreBoard.setLayoutY(15);
        scoreBoard.setStyle(styleOfButton);
        scoreBoard.setText("Scoreboard");
        Button changeAvatarButton = new Button();
        changeAvatarButton.setLayoutX(460);
        changeAvatarButton.setLayoutY(90);
        changeAvatarButton.setStyle(styleOfButton);
        changeAvatarButton.setText("Change Avatar");
        Button back = new Button();
        back.setLayoutX(460);
        back.setLayoutY(645);
        back.setStyle(styleOfButton);
        back.setText("back");
        this.descriptionText = descriptionText;
        this.pane.getChildren().add(back);
        this.pane.getChildren().add(changeAvatarButton);
        this.pane.getChildren().add(changePassButton);
        this.pane.getChildren().add(changeUsernameButton);
        this.pane.getChildren().add(changeEmainButton);
        this.pane.getChildren().add(changeNicknameButton);
        this.pane.getChildren().add(changeSloganButton);
        this.pane.getChildren().add(removeSloganButton);
        this.pane.getChildren().add(descriptionText);
        this.pane.getChildren().add(avatar);
        this.pane.getChildren().add(scoreBoard);

        avatar.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (dragEvent.getDragboard().hasFiles()) {
                    dragEvent.acceptTransferModes(TransferMode.ANY);
                }
                dragEvent.consume();
            }
        });

        avatar.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                String newPath = event.getDragboard().getFiles().get(0).getAbsolutePath();
                AppData.getCurrentUser().setAvatar(newPath);
                loadImage();
                SaveAndLoad.saveData(AppData.getUsers(), AppData.getUsersDataBaseFilePath());
                event.consume();
            }
        });
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                timelineUsername.stop();
                try {
                    new MainMenu().start(AppData.getStage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        changePassButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                initializeDialogOfPassword();
            }
        });
        removeSloganButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ProfileMenuController.removeSlogan();
                String slogan = AppData.getCurrentUser().getSlogan();
                if (slogan.length() < 1) {
                    slogan = "Slogan is empty";
                }
                descriptionText.setText(AppData.getCurrentUser().username + "\n" + AppData.getCurrentUser().password + "\n" +
                        AppData.getCurrentUser().nickname + "\n" + AppData.getCurrentUser().email + "\n" +
                        slogan);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful operation");
                alert.setContentText("Your slogan was removed successfully");
                alert.showAndWait();
            }
        });

        changeSloganButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ProfileMenuController.changeSlogan(sloganTextField.getText());
                String slogan = AppData.getCurrentUser().getSlogan();
                if (slogan.length() < 1) {
                    slogan = "Slogan is empty";
                }
                System.out.println(slogan + "\n" + AppData.getCurrentUser().getSlogan());
                descriptionText.setText(AppData.getCurrentUser().username + "\n" + AppData.getCurrentUser().password + "\n" +
                        AppData.getCurrentUser().nickname + "\n" + AppData.getCurrentUser().email + "\n" +
                        slogan);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful operation");
                alert.setContentText("Your slogan was changed successfully");
                alert.showAndWait();
            }
        });

        changeNicknameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ProfileMenuController.changeNickname(nicknameTextField.getText());
                String slogan = AppData.getCurrentUser().getSlogan();
                if (slogan.length() < 1) {
                    slogan = "Slogan is empty";
                }
                System.out.println(AppData.getCurrentUser().getNickname());
                descriptionText.setText(AppData.getCurrentUser().username + "\n" + AppData.getCurrentUser().password + "\n" +
                        AppData.getCurrentUser().nickname + "\n" + AppData.getCurrentUser().email + "\n" +
                        slogan);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful operation");
                alert.setContentText("Your nickname was changed successfully");
                alert.showAndWait();
            }
        });

        changeUsernameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String output = ProfileMenuController.changeUsername(usernameTextfield.getText());
                if (output.equals("Check format of your username")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Bad Format");
                    alert.setContentText(output);
                    alert.showAndWait();
                }
                else if (output.equals("This username is already exist!")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Bad Username");
                    alert.setContentText(output);
                    alert.showAndWait();
                }
                else {
                    String slogan = AppData.getCurrentUser().getSlogan();
                    if (slogan.length() < 1) {
                        slogan = "Slogan is empty";
                    }
                    descriptionText.setText(AppData.getCurrentUser().username + "\n" + AppData.getCurrentUser().password + "\n" +
                            AppData.getCurrentUser().nickname + "\n" + AppData.getCurrentUser().email + "\n" +
                            slogan);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful operation");
                    alert.setContentText("Your username was changed successfully");
                    alert.showAndWait();
                }
            }
        });

        changeEmainButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String output = ProfileMenuController.changeEmail(emailTextField.getText());
                if (output.equals("Check format of your email")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Wrong Format");
                    alert.setContentText(output);
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful Operation");
                    alert.setContentText(output);
                    alert.showAndWait();
                    String slogan = AppData.getCurrentUser().getSlogan();
                    if (slogan.length() < 1) {
                        slogan = "Slogan is empty";
                    }
                    descriptionText.setText(AppData.getCurrentUser().username + "\n" + AppData.getCurrentUser().password + "\n" +
                            AppData.getCurrentUser().nickname + "\n" + AppData.getCurrentUser().email + "\n" +
                            slogan);
                }
            }
        });

        scoreBoard.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                initializeScrollBoard();
            }
        });

        changeAvatarButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FileChooser fileChooser = new FileChooser();
                File selectedFile = fileChooser.showOpenDialog(AppData.getStage());
                ProfileMenuController.setMyAvatar(selectedFile);
                loadImage();
            }
        });
    }

    public void loadImage() {
        avatar.setFill(new ImagePattern(new Image(AppData.getCurrentUser().getAvatar())));
    }


    private void initializeTextField() {
        String styleOfTextField = "-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                "-fx-background-insets: 50;" + "-fx-border-color: linear-gradient(#ffffff, #000000);" + "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: white";
        TextField usernameTextField = new TextField();
        usernameTextField.setLayoutX(370);
        usernameTextField.setLayoutY(170);
        usernameTextField.setStyle(styleOfTextField);
        usernameTextField.setPromptText("New Username");
        Text usernameText = new Text();
        usernameText.setLayoutX(370);
        usernameText.setLayoutY(207);
        usernameText.setFill(Color.RED);
        this.usernameText = usernameText;
        TextField emailTextField = new TextField();
        emailTextField.setLayoutX(370);
        emailTextField.setLayoutY(470);
        emailTextField.setStyle(styleOfTextField);
        emailTextField.setPromptText("New Email");
        TextField nicknameTextField = new TextField();
        nicknameTextField.setLayoutX(370);
        nicknameTextField.setLayoutY(370);
        nicknameTextField.setStyle(styleOfTextField);
        nicknameTextField.setPromptText("New Nickname");
        TextField sloganTextField = new TextField();
        sloganTextField.setLayoutX(370);
        sloganTextField.setLayoutY(570);
        sloganTextField.setStyle(styleOfTextField);
        sloganTextField.setPromptText("New Slogan");
        this.usernameTextfield = usernameTextField;
        this.emailTextField = emailTextField;
        this.nicknameTextField = nicknameTextField;
        this.sloganTextField = sloganTextField;
        this.pane.getChildren().add(usernameTextField);
        this.pane.getChildren().add(nicknameTextField);
        this.pane.getChildren().add(emailTextField);
        this.pane.getChildren().add(sloganTextField);
        this.pane.getChildren().add(usernameText);
    }

    private void checkingUserNameBox() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {
            initializedUsername();
        }));
        this.timelineUsername = timeline;
        timeline.setCycleCount(-1);
        timeline.play();
    }

    public void initializedUsername() {

        if (LoginMenuController.checkFormatOfUsername(usernameTextfield.getText()) == 0) {
            usernameText.setText("Invalid Format");
        }
        else {
            usernameText.setText("");
        }

    }

    private void initializeDialogOfPassword() {
        ButtonType buttonType = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
        Dialog<String> dialog = new Dialog<String>();
        checkingPasswordBox();
        this.changePassDialog = dialog;
        PasswordField oldPassField = new PasswordField();
        PasswordField newPassField = new PasswordField();
        oldPassField.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                "-fx-background-insets: 50;" + "-fx-border-color: linear-gradient(#ffffff, #000000);" + "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: white");
        newPassField.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                "-fx-background-insets: 50;" + "-fx-border-color: linear-gradient(#ffffff, #000000);" + "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: white");
        dialog.setContentText("Change Password");
        Pane pane1 = new Pane();
        dialog.getDialogPane().setPrefHeight(300);
        dialog.getDialogPane().setPrefWidth(1080);
        dialog.getDialogPane().getButtonTypes().add(buttonType);
        oldPassField.setLayoutX(100);
        oldPassField.setLayoutY(100);
        newPassField.setLayoutY(100);
        newPassField.setLayoutX(300);
        oldPassField.setPromptText("Old Password");
        newPassField.setPromptText("New Password");
        Button changePasswordButton = new Button();
        changePasswordButton.setLayoutX(500);
        changePasswordButton.setLayoutY(80);
        changePasswordButton.setStyle("-fx-effect: dropshadow(gaussian, red, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: black;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 30px;");
        changePasswordButton.setText("Change");
        Text passwordText = new Text();
        passwordText.setLayoutX(300);
        passwordText.setLayoutY(137);
        passwordText.setFill(Color.RED);
        this.passwordText = passwordText;
        this.newPasswordTextField = newPassField;
        this.oldPasswordTextField = oldPassField;
        pane1.getChildren().add(changePasswordButton);
        pane1.getChildren().add(oldPassField);
        pane1.getChildren().add(newPassField);
        pane1.getChildren().add(passwordText);
        changePasswordButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String output = ProfileMenuController.changePassword(oldPasswordTextField.getText(), newPasswordTextField.getText());
                if (output.equals("Enter your old password correctly!")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Wrong Old Pass");
                    alert.setContentText(output);
                    alert.showAndWait();
                }
                else if (output.equals("Choose different new password!")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Wrong New Pass");
                    alert.setContentText(output);
                    alert.showAndWait();
                }
                else if (output.equals("Your new password is weak!")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Weak New Pass");
                    alert.setContentText(output);
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful Operation");
                    alert.setContentText(output);
                    alert.showAndWait();
                    timelinePassword.stop();
                    dialog.close();
                    String slogan = AppData.getCurrentUser().getSlogan();
                    if (slogan.length() < 1) {
                        slogan = "Slogan is empty";
                    }
                    descriptionText.setText(AppData.getCurrentUser().username + "\n" + AppData.getCurrentUser().password + "\n" +
                            AppData.getCurrentUser().nickname + "\n" + AppData.getCurrentUser().email + "\n" +
                            slogan);
                }
            }
        });
        dialog.getDialogPane().setContent(pane1);
        dialog.showAndWait();
        timelinePassword.stop();
    }

    private void checkingPasswordBox() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {
            initializedPassword();
        }));
        timelinePassword = timeline;
        timeline.setCycleCount(-1);
        timeline.play();
    }

    public void initializedPassword() {

        if (LoginMenuController.checkWeakPassword(newPasswordTextField.getText()) == 0 && newPasswordTextField.isVisible()) {
            passwordText.setText("Your password was short!");
        }


        else if (LoginMenuController.checkWeakPassword(newPasswordTextField.getText()) == 1 && newPasswordTextField.isVisible()) {
            passwordText.setText("Your password was week!");
        }


        else {
            passwordText.setText("");
        }

    }

    private void initializeScrollBoard() {
        Dialog dialog = new Dialog<>();
        Pane pane1 = new Pane();
        pane1.setPrefWidth(1080);
        pane1.setPrefHeight(720);

        ButtonType buttonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonType);

        LeaderBoard leaderBoard = new LeaderBoard();
        leaderBoard.setup();
        leaderBoard.setPrefWidth(300);
        leaderBoard.setPrefHeight(275);
        leaderBoard.setLayoutX(390);
        leaderBoard.setLayoutY(200);
        pane1.getChildren().add(leaderBoard);

        dialog.getDialogPane().setContent(pane1);
        dialog.showAndWait();
    }
}
