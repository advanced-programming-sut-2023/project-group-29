package view.menus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.AppData;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class LobbiesMenu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Pane pane;
    private ScrollPane lobbyNamesScrollPane;

    @Override
    public void start(Stage stage) throws Exception {
        pane = FXMLLoader.load(new URL(LobbyMenu.class.getResource("/simpleMenu.fxml").toString()));
        stage.setScene(new Scene(pane));
        stage.show();
        makeNewButton();
        makeRefreshButton();
        makeLobbiesList();
        makeJoinButton();
        makeBackButton();
    }

    private void makeBackButton() {
        Button back = new Button("join");
        back.setTranslateX(100);
        back.setTranslateY(500);
        back.setOnMouseClicked(mouseEvent -> {
            try {
                new PreGameMenu().start(AppData.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        setStyle(back);
        pane.getChildren().add(back);
    }

    private void setStyle(Button button) {
        button.setStyle("-fx-effect: dropshadow(gaussian, red, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: black;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 16px;");
    }

    private void makeJoinButton() {
        TextField lobbyName = new TextField();
        lobbyName.setPromptText("lobby name");
        lobbyName.setTranslateX(200);
        lobbyName.setTranslateY(400);
        Button joinButton = new Button("join");
        joinButton.setTranslateX(100);
        joinButton.setTranslateY(400);
        joinButton.setOnMouseClicked(mouseEvent -> joinFunction(lobbyName.getText()));
        pane.getChildren().add(joinButton);
        pane.getChildren().add(lobbyName);
        setStyle(joinButton);
    }

    private void makeLobbiesList() throws IOException {
        buildScrollPane();
        updateLobbyNames();
    }

    private void buildScrollPane() {
        lobbyNamesScrollPane = new ScrollPane();
        lobbyNamesScrollPane.setTranslateX(500);
        lobbyNamesScrollPane.setTranslateY(100);
        lobbyNamesScrollPane.setPrefSize(150, 500);
        lobbyNamesScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        pane.getChildren().add(lobbyNamesScrollPane);
    }

    private void updateLobbyNames() {
        HashMap<String, String> lobbies = null;
        try {
            lobbies = AppData.getClient().getLobbiesNames();
        } catch (IOException e) {
            e.printStackTrace();
        }
        VBox vBox = new VBox();
        for (String lobby : lobbies.keySet()) {
            Text text = new Text(lobby + " -> " + lobbies.get(lobby));
            text.setOnMouseClicked(mouseEvent -> joinFunction(lobby));
            vBox.getChildren().add(text);
        }
        lobbyNamesScrollPane.setContent(vBox);
    }

    private void joinFunction(String lobbyName) {
        try {
            if (AppData.getClient().joinLobby(lobbyName)) new LobbyMenu(AppData.getClient()).start(AppData.getStage());
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("join failed");
                alert.setContentText("join process failed");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeRefreshButton() {
        Button refreshButton = new Button("refresh");
        refreshButton.setTranslateX(100);
        refreshButton.setTranslateY(300);
        refreshButton.setOnMouseClicked(mouseEvent -> updateLobbyNames());
        pane.getChildren().add(refreshButton);
    }

    private void makeNewButton() {
        TextField numberOfPlayer = new TextField();
        numberOfPlayer.setPromptText("number of players");
        numberOfPlayer.setTranslateX(200);
        numberOfPlayer.setTranslateY(200);
        Button button = new Button("new");
        button.setTranslateX(100);
        button.setTranslateY(200);
        button.setOnMouseClicked(mouseEvent -> makeNewLobby(numberOfPlayer));
        pane.getChildren().add(numberOfPlayer);
        pane.getChildren().add(button);
    }

    private void makeNewLobby(TextField numberOfPlayer) {
        if (!numberOfPlayer.getText().matches("\\d+")){
            showInvalidNumberAlert();
        }
        else {
            int number = Integer.parseInt(numberOfPlayer.getText());
            if (number > 8 || number < 2) {
                showInvalidNumberAlert();
            }
            try {
                AppData.getClient().newLobby(Integer.parseInt(numberOfPlayer.getText()));
                new LobbyMenu(AppData.getClient()).start(AppData.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void showInvalidNumberAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid number");
        alert.setContentText("choose a number between 2 and 8!");
        alert.showAndWait();
    }
}
