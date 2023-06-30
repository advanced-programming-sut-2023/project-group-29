package view.menus;

import controller.menucontrollers.GameController;
import controller.menucontrollers.TradeMenuController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.Command;

import java.net.URL;

public class TradeMenu extends Application {
    @FXML
    private Pane pane;

    private static String showTradeList() {
        return TradeMenuController.showTradeList();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = Command.class.getResource("/FXML/TradeMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        String typeOfButton = "-fx-effect: dropshadow(gaussian, red, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: black;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 50px";
        Button newTrade = new Button();
        newTrade.setLayoutX(420);
        newTrade.setLayoutY(220);
        newTrade.setText("New Trade");
        newTrade.setStyle(typeOfButton);
        Button historyOfTrade = new Button();
        historyOfTrade.setLayoutX(370);
        historyOfTrade.setLayoutY(320);
        historyOfTrade.setText("History Of Trade");
        historyOfTrade.setStyle(typeOfButton);
        this.pane.getChildren().add(newTrade);
        this.pane.getChildren().add(historyOfTrade);

        newTrade.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                initializeDialogOfNewTrade();
            }
        });

        historyOfTrade.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                initializeDialogOfHistory();
            }
        });

        Button back = new Button();
        back.setText("Back");
        back.setLayoutX(460);
        back.setLayoutY(500);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameController.getGameData().getGameGraphicFunctions().exitPopUp();
            }
        });
        this.pane.getChildren().add(back);
    }

    private void initializeDialogOfHistory() {
        String buttonStyle = "-fx-effect: dropshadow(gaussian, blue, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: white;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 40px;";
        Dialog dialog = new Dialog<>();
        ButtonType buttonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonType);
        Pane pane1 = new Pane();
        pane1.setPrefHeight(300);
        pane1.setPrefWidth(600);
        Button receiveTrade = new Button();
        receiveTrade.setLayoutY(150);
        receiveTrade.setLayoutX(50);
        receiveTrade.setText("Receive Trades");
        Button myTrade = new Button();
        myTrade.setLayoutY(150);
        myTrade.setLayoutX(370);
        myTrade.setText("My Trades");
        myTrade.setStyle(buttonStyle);
        receiveTrade.setStyle(buttonStyle);
        pane1.getChildren().add(receiveTrade);
        pane1.getChildren().add(myTrade);
        dialog.getDialogPane().setContent(pane1);
        receiveTrade.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Dialog dialog1 = new Dialog<>();
                ButtonType buttonType1 = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                Pane pane2 = new Pane();
                pane2.prefHeight(400);
                pane2.setPrefWidth(600);
                Text text = new Text(showTradeList());
                text.setLayoutX(200);
                text.setLayoutY(200);
                TextField idOfTrade = new TextField();
                idOfTrade.setPromptText("Id Of Trade");
                idOfTrade.setLayoutY(300);
                idOfTrade.setLayoutX(200);
                Button acceptTrade = new Button();
                acceptTrade.setText("Accept");
                acceptTrade.setLayoutX(400);
                acceptTrade.setLayoutY(300);
                pane2.getChildren().add(acceptTrade);
                pane2.getChildren().add(idOfTrade);
                acceptTrade.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        String output = TradeMenuController.acceptTrade(Integer.parseInt(idOfTrade.getText()));
                        if (output.equals("Wrong id!")) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText(output);
                            alert.showAndWait();
                        }
                        else if (output.equals("Your wealth is less than the price of your dealing!")) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText(output);
                            alert.showAndWait();
                        }
                        else if (output.equals("The sender doesn't have enough commodity now!")) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText(output);
                            alert.showAndWait();
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText(output);
                            alert.showAndWait();
                        }
                    }
                });
                dialog1.getDialogPane().setContent(pane2);
                dialog1.getDialogPane().getButtonTypes().add(buttonType1);
                dialog1.showAndWait();
            }
        });

        myTrade.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Dialog dialog1 = new Dialog<>();
                ButtonType buttonType1 = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                Pane pane2 = new Pane();
                pane2.prefHeight(400);
                pane2.setPrefWidth(600);
                Text text = new Text(TradeMenuController.showTradeHistory());
                text.setLayoutX(200);
                text.setLayoutY(200);
                pane2.getChildren().add(text);
                dialog1.getDialogPane().setContent(pane2);
                dialog1.getDialogPane().getButtonTypes().add(buttonType1);
                dialog1.showAndWait();
            }
        });
        dialog.showAndWait();
    }

    private void initializeDialogOfNewTrade() {
        ButtonType buttonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        Dialog dialog = new Dialog<>();
        Pane pane1 = new Pane();
        String players = "";
        //for(int i = 0; i < GameController.getGameData().getEmpires().size(); i++) {
        //players += GameController.getGameData().getEmpires().get(0).getUser().username + "\n";
        //}
        Text text = new Text(players);
        text.setLayoutY(100);
        text.setLayoutX(400);
        TextField userTextField = new TextField();
        userTextField.setPromptText("Player Name");
        userTextField.setLayoutY(200);
        userTextField.setLayoutX(100);
        userTextField.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);" +
                "-fx-background-insets: 50;" + "-fx-border-color: linear-gradient(#ffffff, #000000);" + "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: white");
        Button button = new Button();
        button.setText("Confirm");
        button.setStyle("-fx-effect: dropshadow(gaussian, blue, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: white;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 30px;");
        button.setLayoutX(250);
        button.setLayoutY(180);
        pane1.getChildren().add(button);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (int i = 0; i < GameController.getGameData().getEmpires().size(); i++) {
                    if (GameController.getGameData().getEmpires().get(i).getUser().getUsername().equals(userTextField.getText())) {
                        int numberOfPlayer = i;
                        ButtonType buttonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        Dialog dialog = new Dialog<>();
                        Pane pane1 = new Pane();
                        pane1.setPrefHeight(400);
                        pane1.setPrefWidth(800);
                        Button apple = new Button();
                        apple.setLayoutY(100);
                        apple.setLayoutX(100);
                        apple.setText("apple");
                        pane1.getChildren().add(apple);
                        Button meat = new Button();
                        meat.setLayoutY(100);
                        meat.setLayoutX(200);
                        meat.setText("meat");
                        pane1.getChildren().add(meat);
                        Button bread = new Button();
                        bread.setLayoutY(100);
                        bread.setLayoutX(300);
                        bread.setText("bread");
                        pane1.getChildren().add(bread);
                        Button cheese = new Button();
                        cheese.setLayoutY(100);
                        cheese.setLayoutX(400);
                        cheese.setText("cheese");
                        pane1.getChildren().add(cheese);
                        Button bow = new Button();
                        bow.setLayoutY(200);
                        bow.setLayoutX(100);
                        bow.setText("bow");
                        pane1.getChildren().add(bow);
                        Button sword = new Button();
                        sword.setLayoutY(200);
                        sword.setLayoutX(200);
                        sword.setText("sword");
                        pane1.getChildren().add(sword);
                        Button armour = new Button();
                        armour.setLayoutY(200);
                        armour.setLayoutX(300);
                        armour.setText("armour");
                        pane1.getChildren().add(armour);
                        Button wood = new Button();
                        wood.setLayoutY(200);
                        wood.setLayoutX(400);
                        wood.setText("wood");
                        pane1.getChildren().add(wood);
                        Button iron = new Button();
                        iron.setLayoutY(300);
                        iron.setLayoutX(100);
                        iron.setText("iron");
                        pane1.getChildren().add(iron);
                        Button stone = new Button();
                        stone.setLayoutY(300);
                        stone.setLayoutX(200);
                        stone.setText("stone");
                        pane1.getChildren().add(stone);
                        Button pitch = new Button();
                        pitch.setLayoutY(300);
                        pitch.setLayoutX(300);
                        pitch.setText("pitch");
                        pane1.getChildren().add(pitch);
                        Button horse = new Button();
                        horse.setLayoutY(300);
                        horse.setLayoutX(400);
                        horse.setText("horse");
                        pane1.getChildren().add(horse);
                        TextField message = new TextField();
                        message.setPromptText("Message");
                        Button plus = new Button();
                        plus.setLayoutY(200);
                        plus.setLayoutX(550);
                        plus.setText("+");
                        pane1.getChildren().add(plus);
                        Button minus = new Button();
                        minus.setLayoutY(200);
                        minus.setLayoutX(500);
                        minus.setText("-");
                        Text text1 = new Text("0");
                        text1.setLayoutX(530);
                        text1.setLayoutY(220);
                        Text text2 = new Text();
                        text2.setLayoutY(220);
                        text2.setLayoutX(600);
                        message.setLayoutX(650);
                        message.setLayoutY(200);
                        text2.setText("Resource");
                        Button[] buttons = {apple, bread, meat, cheese, bow, armour, horse, sword, wood, iron, stone, pitch};
                        for (int j = 0; j < buttons.length; j++) {
                            int finalI = j;
                            buttons[j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    text2.setText(buttons[finalI].getText());
                                }
                            });
                        }
                        pane1.getChildren().add(text2);
                        pane1.getChildren().add(message);
                        plus.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                String output = text1.getText();
                                int output1 = Integer.parseInt(output) + 1;
                                text1.setText(String.valueOf(output1));
                            }
                        });

                        minus.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                String output = text1.getText();
                                int output1 = Integer.parseInt(output) - 1;
                                text1.setText(String.valueOf(output1));
                            }
                        });

                        Button trade = new Button();
                        trade.setText("Trade");
                        trade.setLayoutX(660);
                        trade.setLayoutY(250);

                        Button donate = new Button();
                        donate.setText("Donate");
                        donate.setLayoutX(540);
                        donate.setLayoutY(250);
                        TextField textField = new TextField();
                        textField.setPromptText("Price");
                        textField.setLayoutX(540);
                        textField.setLayoutY(300);
                        trade.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                String output = TradeMenuController.trade(text2.getText(), text1.getText(), textField.getText(), message.getText(), numberOfPlayer);
                                if (output.equals("You don't have enough commodity!")) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setContentText(output);
                                    alert.showAndWait();
                                }
                                else {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText(output);
                                    alert.showAndWait();
                                }
                            }
                        });

                        donate.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                String output = TradeMenuController.trade(text2.getText(), text1.getText(), textField.getText(), message.getText(), numberOfPlayer);
                                if (output.equals("You don't have enough commodity!")) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setContentText(output);
                                    alert.showAndWait();
                                }
                                else {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText(output);
                                    alert.showAndWait();
                                }
                            }
                        });
                        pane1.getChildren().add(textField);
                        pane1.getChildren().add(donate);
                        pane1.getChildren().add(trade);
                        pane1.getChildren().add(text1);
                        pane1.getChildren().add(minus);
                        dialog.getDialogPane().getButtonTypes().add(buttonType);
                        dialog.getDialogPane().setContent(pane1);
                        dialog.showAndWait();
                    }
                }
            }
        });
        pane1.getChildren().add(userTextField);
        pane1.getChildren().add(text);
        pane1.setPrefHeight(400);
        pane1.setPrefWidth(600);
        dialog.getDialogPane().setContent(pane1);
        dialog.getDialogPane().getButtonTypes().add(buttonType);
        dialog.showAndWait();
    }
}
