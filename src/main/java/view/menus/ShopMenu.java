package view.menus;

import controller.menucontrollers.GameController;
import controller.menucontrollers.ShopMenuController;
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
import model.GamePopUpMenus;
import model.dealing.Resource;
import view.Command;
import view.messages.ShopMenuMessages;

import java.io.IOException;
import java.net.URL;

public class ShopMenu extends Application {

    @FXML
    private Pane pane;

    private static void buy(String type, int amount) {

        ShopMenuMessages result = ShopMenuController.buy(type, amount);
        switch (result) {
            case INVALID_NAME -> {
            }
            case SUCCESS -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful operation");
                alert.setContentText("Your buying was successful");
                alert.showAndWait();
            }
            case FEW_CASH -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Not Enough Money");
                alert.setContentText("You don't have enough money!");
                alert.showAndWait();
            }
            case LACK_OF_SPACE -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Not Enough Space");
                alert.setContentText("You don't have enough space for storage!");
                alert.showAndWait();
            }
        }
    }

    private static void sell(String type, int amount) {
        ShopMenuMessages result = ShopMenuController.sell(type, amount);
        switch (result) {
            case INVALID_NAME -> {
                System.out.println("We don't have any resource with this name!");
            }
            case SUCCESS -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful operation");
                alert.setContentText("Your selling was successful");
                alert.showAndWait();
            }
            case FEW_AMOUNT -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Not Enough Amount");
                alert.setContentText("The amount you have entered is greater than the amount you have!");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = Command.class.getResource("/FXML/ShopMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        String buttonStyle = "-fx-effect: dropshadow(gaussian, red, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: black;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 40px";
        Button buttonOfStone = new Button();
        buttonOfStone.setText("Stone");
        buttonOfStone.setStyle(buttonStyle);
        buttonOfStone.setLayoutX(100);
        buttonOfStone.setLayoutY(100);
        this.pane.getChildren().add(buttonOfStone);
        Button buttonOfIron = new Button();
        buttonOfIron.setText("Iron");
        buttonOfIron.setStyle(buttonStyle);
        buttonOfIron.setLayoutX(350);
        buttonOfIron.setLayoutY(100);
        this.pane.getChildren().add(buttonOfIron);
        Button buttonOfPitch = new Button();
        buttonOfPitch.setText("Pitch");
        buttonOfPitch.setStyle(buttonStyle);
        buttonOfPitch.setLayoutX(600);
        buttonOfPitch.setLayoutY(100);
        this.pane.getChildren().add(buttonOfPitch);
        Button buttonOfWood = new Button();
        buttonOfWood.setText("Wood");
        buttonOfWood.setStyle(buttonStyle);
        buttonOfWood.setLayoutX(850);
        buttonOfWood.setLayoutY(100);
        this.pane.getChildren().add(buttonOfWood);
        Button buttonOfTrade = new Button();
        buttonOfTrade.setText("Trade Menu");
        buttonOfTrade.setStyle(buttonStyle);
        buttonOfTrade.setLayoutX(410);
        buttonOfTrade.setLayoutY(300);
        this.pane.getChildren().add(buttonOfTrade);

        Button back = new Button();
        back.setText("Back");
        back.setStyle(buttonStyle);
        back.setLayoutX(460);
        back.setLayoutY(500);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameController.getGameData().getGameGraphicFunctions().exitPopUp();
            }
        });
        this.pane.getChildren().add(back);

        buttonOfStone.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dialogOfStone(Resource.STONE);
            }
        });

        buttonOfIron.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dialogOfStone(Resource.IRON);
            }
        });

        buttonOfPitch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dialogOfStone(Resource.PITCH);
            }
        });

        buttonOfWood.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dialogOfStone(Resource.WOOD);
            }
        });
        buttonOfTrade.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Pane pane1 = FXMLLoader.load(TradeMenu.class.getResource("/FXML/TradeMenu.fxml"));
                    GameGraphicFunctions gameGraphicFunctions = GameController.getGameData().getGameGraphicFunctions();
                    GamePopUpMenus popUpMenu = new GamePopUpMenus(gameGraphicFunctions.getMainPane(), pane1, GamePopUpMenus.PopUpType.DROP_UNITS);
                    popUpMenu.makePaneCenter(1080, 720);
                    if(gameGraphicFunctions.getPopUpMenu()!=null)
                        gameGraphicFunctions.getPopUpMenu().hide();
                    gameGraphicFunctions.setPopUpMenu(popUpMenu);
                    popUpMenu.showAndWait();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    private void dialogOfStone(Resource resource) {
        String buttonStyle = "-fx-effect: dropshadow(gaussian, red, 10, 0, 0, 0);" +
                "    -fx-background-insets: 50;" +
                "    -fx-text-fill: black;" +
                "    -fx-font-family: \"Brush Script MT\";" +
                "    -fx-font-size: 40px";
        Dialog dialog = new Dialog<>();
        Pane pane1 = new Pane();
        pane1.setPrefWidth(600);
        pane1.setPrefHeight(400);
        Text amount = new Text();
        amount.setLayoutX(270);
        amount.setLayoutY(50);
        amount.setText("You have " + GameController.getGameData().getPlayingEmpire().getTradableAmount(resource) + " " + resource.getName());
        pane1.getChildren().add(amount);
        Button buy = new Button();
        buy.setText("Buy");
        buy.setLayoutX(200);
        buy.setLayoutY(100);
        buy.setStyle(buttonStyle);
        pane1.getChildren().add(buy);
        Button sell = new Button();
        sell.setText("Sell");
        sell.setLayoutX(200);
        sell.setLayoutY(200);
        sell.setStyle(buttonStyle);
        pane1.getChildren().add(sell);
        Text priceOfBuy = new Text();
        priceOfBuy.setText("Price Of Buy: " + resource.getBuyingPrice());
        priceOfBuy.setLayoutY(150);
        priceOfBuy.setLayoutX(350);
        pane1.getChildren().add(priceOfBuy);
        Text priceOfSell = new Text();
        priceOfSell.setText("Price Of Sell: " + resource.getSellingPrice());
        priceOfSell.setLayoutY(250);
        priceOfSell.setLayoutX(350);
        pane1.getChildren().add(priceOfSell);
        ButtonType buttonType = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonType);
        dialog.getDialogPane().setContent(pane1);
        buy.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                buy(resource.getName(), 1);
            }
        });

        sell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                sell(resource.getName(), 1);
            }
        });
        dialog.showAndWait();

    }
}
