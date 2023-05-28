package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.menus.LoginMenu;

import java.net.URL;

public class Main extends Application {
    public static Stage stage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage=stage;

        URL url = LoginMenu.class.getResource("/FXML/MainMenu.fxml");
        Pane pane = FXMLLoader.load(url);

        Scene scene=new Scene(pane);
        stage.setScene(scene);
        stage.show();

        //Controller.run();
    }
}