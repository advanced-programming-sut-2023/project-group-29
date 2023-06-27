package view.menus;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.AppData;
import model.SaveAndLoad;
import model.User;
import view.Command;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class EnterMenu extends Application {

    Background background1 = new Background(new BackgroundImage(new Image(EnterMenu.class.getResource("/images/menus/EnterMenuBackGroundSelect1.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));

    Background background2 = new Background(new BackgroundImage(new Image(EnterMenu.class.getResource("/images/menus/EnterMenuBackGroundSelect2.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
    @Override
    public void start(Stage stage) throws Exception {
        User[] users = SaveAndLoad.loadArrayData(AppData.getUsersDataBaseFilePath(), User[].class);
        if (users != null)
            AppData.setUsers(new ArrayList<>(Arrays.asList(users)));
        AppData.setStage(stage);
        URL url = Command.class.getResource("/FXML/EnterMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Label label = new Label();
        pane.getChildren().add(label);
        pane.setBackground(background1);
        changeBackground(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        pane.getChildren().get(0).requestFocus();
        stage.show();
    }
    private void changeBackground(Pane pane) {
        pane.getChildren().get(0).setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName = keyEvent.getCode().getName();
                if(keyName.equals("Up")){
                    pane.setBackground(background1);
                }
                else if(keyName.equals("Down")){
                    pane.setBackground(background2);
                }
                else if(keyName.equals("Enter")) {
                    if(pane.getBackground().equals(background1)) {
                        try {
                            EnterMenuGraphic.signIn();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if(pane.getBackground().equals(background2)) {
                        try {
                            EnterMenuGraphic.signUp();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }
}
