package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Client;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainMenu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Stage stage;
    private Pane pane;
    private Client client;
    private ScrollPane lobbyNamesScrollPane;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        pane = FXMLLoader.load(new URL(LobbyMenu.class.getResource("/simpleMenu.fxml").toString()));
        stage.setScene(new Scene(pane));
        stage.show();
        client = new Client(stage);
        makeNewButton();
        makeRefreshButton();
        makeLobbiesList();
        makeJoinButton();
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
            lobbies = client.getLobbiesNames();
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

    private void joinFunction(String lobby) {
        try {
            if (client.joinLobby(lobby)) new LobbyMenu(client).start(stage);
//            else {todo alert message error}
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
        if (!numberOfPlayer.getText().matches("\\d+")){} //todo alert message error
        else {
            int number = Integer.parseInt(numberOfPlayer.getText());
            if (number > 8 || number < 2) {
                //todo alert message error
            }
            try {
                client.newLobby(Integer.parseInt(numberOfPlayer.getText()));
                new LobbyMenu(client).start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
