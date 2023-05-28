package view.menus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.Command;

import java.net.URL;

public class RegisterMenu extends Application {
    private static Pane pane;
    @Override
    public void start(Stage stage) throws Exception {
        URL url = Command.class.getResource("/FXML/RegisterMenu.fxml");
        BorderPane borderPane = FXMLLoader.load(url);
        this.pane = borderPane;
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    public static Pane getPane() {
        return pane;
    }
}
