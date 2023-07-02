package view.menus;

import controller.menucontrollers.GameController;
import controller.menucontrollers.PreGameMenuController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.AppData;
import model.Empire;
import model.GameData;
import view.Command;
import view.messages.PreGameMenuMessages;

import java.net.URL;

public class PreGameMenu extends Application {
    private Pane pane;
    private TextField nameOfMapTextField;
    private Button setMap;

    private static void addPlayerToGame(String username) {
        PreGameMenuMessages result = PreGameMenuController.addUserToGame(username);
        switch (result) {
            case INVALID_USER -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wrong Username");
                alert.setContentText("User with this username doesn't exist!");
                alert.showAndWait();
            }
            case USER_EXIST -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wrong Username");
                alert.setContentText("You have already added this user!");
                alert.showAndWait();
            }
            case FILLED_TO_CAPACITY -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wrong Action");
                alert.setContentText("The maximum number of player is 8!");
                alert.showAndWait();
            }
            case SUCCESS -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful Operation");
                alert.setContentText("The player was added successfully!");
                alert.showAndWait();
            }
        }
    }

    private static void chooseMap(String name) {
        PreGameMenuMessages result = PreGameMenuController.chooseMap(name);
        switch (result) {
            case FEW_PLAYER -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Less Or More Player");
                alert.setContentText("Number of players is not proper for this map!");
                alert.showAndWait();
            }
            case INVALID_MAP -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wrong Number Of Map");
                alert.setContentText("No map exists with this index!");
                alert.showAndWait();
            }
            case SUCCESS -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Map is chosen successfully!");
                alert.showAndWait();
            }
        }
    }

    private static boolean notReady() {
        PreGameMenuMessages result = PreGameMenuController.isGameDataReady();
        switch (result) {
            case NOT_CHOSEN_MAP -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Choose Map");
                alert.setContentText("You haven't still chosen the map!");
                alert.showAndWait();
                return true;
            }
            case READY -> {
                PreGameMenuController.setPlayerNumbersAlive();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("The game started. GOOD LUCK!");
                alert.showAndWait();
            }
        }
        return false;
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = Command.class.getResource("/FXML/PreGameMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        this.pane = pane;
        initialize();

        GameController.setGameData(new GameData());
        GameController.getGameData().addEmpire(new Empire(AppData.getCurrentUser()));

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void initialize() {
        nameOfMapTextField = new TextField();
        nameOfMapTextField.setPromptText("Map Name");
        nameOfMapTextField.setLayoutX(450);
        nameOfMapTextField.setLayoutY(321);
        nameOfMapTextField.setMaxWidth(200);
        nameOfMapTextField.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                "-fx-background-insets: 50;" + "-fx-border-color: linear-gradient(#ffffff, #000000);" + "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: white");
        Button setMap = new Button();
        setMap.setStyle("-fx-effect: dropshadow(gaussian, red, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: black;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 16px;");
        setMap.setLayoutX(630);
        setMap.setLayoutY(321);
        setMap.setText("Set Map");
        this.setMap = setMap;
        this.pane.getChildren().add(nameOfMapTextField);
        this.pane.getChildren().add(setMap);
        setMap.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                chooseMap(nameOfMapTextField.getText());
            }
        });
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(AppData.getStage());
    }

    public void play(MouseEvent mouseEvent) throws Exception {
        if (!notReady()) {
            new LobbiesMenu().start(AppData.getStage());
        }
    }
}
