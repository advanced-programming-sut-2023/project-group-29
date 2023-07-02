package view.menus;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.AppData;
import view.Command;

import java.net.URL;

public class MainMenu extends Application {
    BackgroundSize backgroundSize = new BackgroundSize(1080, 720, false, false, false, false);
    Background background1 = new Background(new BackgroundImage(new Image(EnterMenu.class.getResource("/images/menus/MainMenuBack1.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize));
    Background background2 = new Background(new BackgroundImage(new Image(EnterMenu.class.getResource("/images/menus/MainMenuBack2.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize));
    Background background3 = new Background(new BackgroundImage(new Image(EnterMenu.class.getResource("/images/menus/MainMenuBack3.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize));

    private int numberOfBack = 1;

    @Override
    public void start(Stage stage) throws Exception {
        URL url = Command.class.getResource("/FXML/MainMenu.fxml");
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
                if (keyName.equals("Up") && numberOfBack != 1) {
                    numberOfBack--;
                }
                else if (keyName.equals("Down") && numberOfBack != 3) {
                    numberOfBack++;
                }
                else if (keyName.equals("Enter")) {
                    if (pane.getBackground().equals(background1)) {
                        try {
                            new LobbiesMenu().start(AppData.getStage());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else if (pane.getBackground().equals(background2)) {
                        try {
                            new ProfileMenu().start(AppData.getStage());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else if (pane.getBackground().equals(background3)) {
                        AppData.setCurrentUser(null);
                        try {
                            new EnterMenu().start(AppData.getStage());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (numberOfBack == 1) {
                    pane.setBackground(background1);
                }
                else if (numberOfBack == 2) {
                    pane.setBackground(background2);
                }
                else {
                    pane.setBackground(background3);
                }
            }
        });
    }

    public void editMapMenu(MouseEvent mouseEvent) throws Exception {
        EditMapMenuGraphics editMapMenuGraphics=new EditMapMenuGraphics();
        editMapMenuGraphics.start(AppData.getStage());
    }
}
