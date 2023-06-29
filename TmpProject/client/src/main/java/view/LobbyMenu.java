package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LobbyMenu extends Application {
    private String lobbyName;
    private ArrayList<String> usernames;
    private Pane pane;
    private Stage stage;
    private Client client;

    public LobbyMenu(Client client) throws IOException {
        this.usernames = client.getUsernames();
        this.lobbyName = client.getLobbyName();
        this.client = client;
        Timer refreshTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (client.isLobbyValid()) {
                        new LobbyMenu(client).start(stage);
                    } else {
                        new MainMenu().start(stage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
    }

    @Override
    public void start(Stage stage) throws Exception {
        pane = FXMLLoader.load(new URL(LobbyMenu.class.getResource("/simpleMenu.fxml").toString()));
        stage.setScene(new Scene(pane));
        stage.show();
        this.stage = stage;
        makeLeftButton();
        makeLobbyName();
        if (client.isAdmin()) makePlayButton();
        if (client.isAdmin()) makeAccessButton();
        updateUsers();
    }

    private void makeLobbyName() {
        Label label = new Label(lobbyName);
        pane.getChildren().add(label);
        label.setTranslateX(500);
        label.setStyle("-fx-text-fill: black;" + "-fx-font-size: 30px");
    }

    private void makeAccessButton() {
        Button button = new Button("private/public");
        button.setTranslateX(600);
        button.setTranslateY(300);
        button.setOnMouseClicked(mouseEvent -> client.changeLobbyAccess());
        pane.getChildren().add(button);
    }

    private void makePlayButton() throws IOException {
        Button button = new Button("start play");
        button.setTranslateX(700);
        button.setTranslateY(300);
        pane.getChildren().add(button);
        client.startGame();
    }

    private void makeLeftButton() {
        Button button = new Button("left");
        button.setTranslateX(800);
        button.setTranslateY(300);
        button.setOnMouseClicked(mouseEvent -> client.leftLobby());
        pane.getChildren().add(button);
    }

    private void updateUsers() {
        VBox vBox = new VBox();
        vBox.setSpacing(15);
        vBox.setTranslateX(100);
        vBox.setTranslateY(100);
        for (String username : usernames) {
            Text text = new Text(username);
            beautify(text);
            vBox.getChildren().add(text);
        }
        pane.getChildren().add(vBox);
    }

    private void beautify(Text text) {
        text.setStyle("-fx-text-fill: black;" + "-fx-font-size: 18px");
    }
}
