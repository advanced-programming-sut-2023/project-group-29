package view.menus;

import controller.menucontrollers.GameController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.AppData;
import model.Message;
import view.Command;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatRoom extends Application {
    @FXML
    private Pane pane;
    @Override
    public void start(Stage stage) throws Exception {
        URL url = Command.class.getResource("/FXML/ChatRoom.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize(){
        String textFieldStyle = "-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                "-fx-background-insets: 50;" + "-fx-border-color: linear-gradient(#ffffff, #000000);" + "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: white";
        String buttonStyle = "-fx-effect: dropshadow(gaussian, red, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: black;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 25px;";
        Image backFlash = new Image(Command.class.getResource("/images/ChatSymbols/backFlash.png").toString());
        Circle back = new Circle(1030, 50, 50);
        back.setFill(new ImagePattern(backFlash));
        pane.getChildren().add(back);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new ChatMenu().start(AppData.getStage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        for(int i = 0; i < AppData.getMessagesOfPublicChat().size(); i++) {
            for(int j = 0; j < pane.getChildren().size(); j++) {
                if(pane.getChildren().get(j).getClass().getSimpleName().equals("Text") || pane.getChildren().get(j).getClass().getSimpleName().equals("Rectangle")) {
                    pane.getChildren().get(j).setLayoutY(pane.getChildren().get(j).getLayoutY() - 60);
                }
            }
            Text text = new Text(AppData.getMessagesOfPublicChat().get(i).getText());
            text.setStyle("-fx-font-family: 'Arial Rounded MT';" + "-fx-font-size: 30px");
            text.setLayoutX(10);
            text.setLayoutY(650);
            pane.getChildren().add(text);
            Text name = new Text(AppData.getMessagesOfPublicChat().get(i).getName());
            name.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" + "-fx-font-size: 10px");
            name.setLayoutX(10);
            name.setLayoutY(670);
            pane.getChildren().add(name);
            Text time = new Text(AppData.getMessagesOfPublicChat().get(i).getTime());
            time.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" + "-fx-font-size: 10px");
            time.setLayoutX(70);
            time.setLayoutY(670);
            pane.getChildren().add(time);
            Image image1 = new Image(AppData.getMessagesOfPublicChat().get(i).getAvatar());
            Rectangle avatar1 = new Rectangle(110, 660, 15, 15);
            avatar1.setFill(new ImagePattern(image1));
            pane.getChildren().add(avatar1);
            Image image2 = new Image(Command.class.getResource("/images/ChatSymbols/tick.png").toString());
            Rectangle avatar2 = new Rectangle(130, 660, 15, 15);
            avatar2.setFill(new ImagePattern(image2));
            pane.getChildren().add(avatar2);
            extracted(text, name, time, avatar1, avatar2, buttonStyle, textFieldStyle);
        }
        TextField chatBox = new TextField();
        chatBox.setPromptText("Type Your Message: ");
        chatBox.setLayoutX(5);
        chatBox.setLayoutY(690);
        chatBox.setPrefWidth(400);
        chatBox.setStyle(textFieldStyle);
        this.pane.getChildren().add(chatBox);
        Button send = new Button();
        send.setStyle(buttonStyle);
        send.setLayoutY(677);
        send.setLayoutX(400);
        send.setText("Send");
        this.pane.getChildren().add(send);
        send.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for(int i = 0; i < pane.getChildren().size(); i++) {
                    if(pane.getChildren().get(i).getClass().getSimpleName().equals("Text") || pane.getChildren().get(i).getClass().getSimpleName().equals("Rectangle")) {
                        pane.getChildren().get(i).setLayoutY(pane.getChildren().get(i).getLayoutY() - 60);
                    }
                }
                Text text = new Text(chatBox.getText());
                text.setStyle("-fx-font-family: 'Arial Rounded MT';" + "-fx-font-size: 30px");
                text.setLayoutX(10);
                text.setLayoutY(650);
                pane.getChildren().add(text);
                Text name = new Text(AppData.getCurrentUser().getUsername());
                name.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" + "-fx-font-size: 10px");
                name.setLayoutX(10);
                name.setLayoutY(670);
                pane.getChildren().add(name);
                SimpleDateFormat formatter= new SimpleDateFormat("HH:mm");
                Date date = new Date(System.currentTimeMillis());
                Text time = new Text(formatter.format(date));
                time.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" + "-fx-font-size: 10px");
                time.setLayoutX(70);
                time.setLayoutY(670);
                pane.getChildren().add(time);
                Image image1 = new Image(AppData.getCurrentUser().getAvatar());
                Rectangle avatar1 = new Rectangle(110, 660, 15, 15);
                avatar1.setFill(new ImagePattern(image1));
                pane.getChildren().add(avatar1);
                Message message = new Message(text.getText(), time.getText(), name.getText(), AppData.getCurrentUser().getAvatar());
                AppData.addMessageOfPublicChat(message);
                Image image2 = new Image(Command.class.getResource("/images/ChatSymbols/tick.png").toString());
                Rectangle avatar2 = new Rectangle(130, 660, 15, 15);
                avatar2.setFill(new ImagePattern(image2));
                pane.getChildren().add(avatar2);
                chatBox.setText("");
                extracted(text, name, time, avatar1, avatar2, buttonStyle, textFieldStyle);
            }
        });
    }

    private void extracted(Text text, Text name, Text time, Rectangle avatar1, Rectangle avatar2, String buttonStyle, String textFieldStyle) {
        text.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Button edit = new Button("Edit");
                edit.setStyle(buttonStyle);
                edit.setLayoutX(200);
                edit.setLayoutY(text.getLayoutY() - 50);
                pane.getChildren().add(edit);
                Button remove = new Button("Delete");
                remove.setStyle(buttonStyle);
                remove.setLayoutX(200);
                remove.setLayoutY(text.getLayoutY() - 10);
                pane.getChildren().add(remove);
                edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        edit.setVisible(false);
                        remove.setVisible(false);
                        TextField textField = new TextField(text.getText());
                        text.setVisible(false);
                        textField.setStyle(textFieldStyle);
                        textField.setLayoutX(text.getLayoutX());
                        textField.setLayoutY(text.getLayoutY() - 30);
                        pane.getChildren().add(textField);
                        Button ok = new Button("Ok");
                        ok.setStyle(buttonStyle);
                        ok.setLayoutX(200);
                        ok.setLayoutY(text.getLayoutY() - 45);
                        pane.getChildren().add(ok);
                        ok.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {

                                for(int i = 0; i < AppData.getMessagesOfPublicChat().size(); i++) {
                                    if(AppData.getMessagesOfPublicChat().get(i).getText().equals(text.getText()) &&
                                            AppData.getMessagesOfPublicChat().get(i).getName().equals(name.getText()) &&
                                            AppData.getMessagesOfPublicChat().get(i).getTime().equals(time.getText())) {
                                        AppData.getMessagesOfPublicChat().get(i).setText(textField.getText());
                                        break;
                                    }
                                }
                                text.setText(textField.getText());
                                text.setVisible(true);
                                textField.setText("");
                                ok.setVisible(false);
                                textField.setVisible(false);
                            }
                        });
                    }
                });

                remove.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        edit.setVisible(false);
                        remove.setVisible(false);
                        for(int i = 0; i < pane.getChildren().size(); i++) {
                            if(pane.getChildren().get(i).equals(text)){
                                break;
                            }
                            if(pane.getChildren().get(i).getClass().getSimpleName().equals("Text") || pane.getChildren().get(i).getClass().getSimpleName().equals("Rectangle")) {
                                pane.getChildren().get(i).setLayoutY(pane.getChildren().get(i).getLayoutY() + 60);
                            }
                        }
                        for(int i = 0; i < AppData.getMessagesOfPublicChat().size(); i++) {
                            if(AppData.getMessagesOfPublicChat().get(i).getText().equals(text.getText()) &&
                                    AppData.getMessagesOfPublicChat().get(i).getName().equals(name.getText()) &&
                                    AppData.getMessagesOfPublicChat().get(i).getTime().equals(time.getText())) {
                                AppData.getMessagesOfPublicChat().remove(AppData.getMessagesOfPublicChat().get(i));
                                break;
                            }
                        }
                        pane.getChildren().remove(text);
                        pane.getChildren().remove(time);
                        pane.getChildren().remove(name);
                        pane.getChildren().remove(avatar1);
                        pane.getChildren().remove(avatar2);
                    }
                });
            }
        });
    }
}
