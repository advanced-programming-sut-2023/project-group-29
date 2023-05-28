package view.menus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.AppData;
import view.Command;

import java.net.URL;

public class EnterMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        AppData.setStage(stage);
        URL url = Command.class.getResource("/FXML/EnterMenu.fxml");
        BorderPane borderPane = FXMLLoader.load(url);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }
}
