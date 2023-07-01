package view.menus;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.AppData;
import view.Command;

import java.awt.*;
import java.net.URL;



public class ChatMenu extends Application {
    @FXML
    private Pane pane;
    @Override
    public void start(Stage stage) throws Exception {
        URL url = Command.class.getResource("/FXML/ChatMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        AppData.setStage(stage);
        stage.show();
    }

    @FXML
    private void initialize(){
        String buttonStyle = "-fx-effect: dropshadow(gaussian, gold, 20, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: green;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 45px";
        Text text = new Text("Chat Menu");
        text.setLayoutX(400);
        text.setLayoutY(90);
        text.setStyle("-fx-font-size: 90px;" + "-fx-font-family: 'Palace Script MT';");
        this.pane.getChildren().add(text);
        Button publicChatButton = new Button("Public Chats");
        publicChatButton.setLayoutX(50);
        publicChatButton.setLayoutY(200);
        publicChatButton.setStyle(buttonStyle);
        this.pane.getChildren().add(publicChatButton);
        Button privateChatButton = new Button("Private Chats");
        privateChatButton.setLayoutX(50);
        privateChatButton.setLayoutY(350);
        privateChatButton.setStyle(buttonStyle);
        this.pane.getChildren().add(privateChatButton);
        Button roomChatButton = new Button("Rooms");
        roomChatButton.setLayoutX(50);
        roomChatButton.setLayoutY(500);
        roomChatButton.setStyle(buttonStyle);
        this.pane.getChildren().add(roomChatButton);
        Button back = new Button("Back");
        back.setLayoutX(50);
        back.setLayoutY(650);
        back.setStyle(buttonStyle);
        this.pane.getChildren().add(back);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new ProfileMenu().start(AppData.getStage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        publicChatButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new ChatRoom().start(AppData.getStage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
