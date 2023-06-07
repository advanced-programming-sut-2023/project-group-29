package view.menus;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.CircleImage;
import model.Empire;

import java.net.URL;

public class GameMenuGraphic extends Application {
    private Pane pane;
    private Stage stage;
    private Popup popularityPopup = new Popup();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        URL url = new URL(GameMenuGraphic.class.getResource("/FXML/GameMenu.fxml").toString());
        pane = FXMLLoader.load(url);
        stage.setScene(new Scene(pane));
        stage.show();
        buildPopularity();
        makeBuildingBar();
    }

    private void makeBuildingBar() {
        VBox vBox = new VBox();
        pane.getChildren().add(vBox);
    }

    private void buildPopularity() {
        //todo beautify
        makeMainPopularity();
        makeFactorsTable();
    }

    private void makeFactorsTable() {
        VBox outerBox = makeOuterBox();
        Text text1 = new Text("Popularity factors:");
        text1.setStyle("-fx-font: 18 arial;");
        HBox factorsHBox = new HBox();
        factorsHBox.setSpacing(50);
        factorsHBox.setAlignment(Pos.CENTER);
        outerBox.getChildren().add(text1);
        outerBox.getChildren().add(factorsHBox);
        for (int i = 1; i <= 4; i++) addVbox(factorsHBox, i);
        Text text = new Text("change for next turn: " + 0); //todo correct number
        outerBox.getChildren().add(text);
        popularityPopup.getContent().add(outerBox);
    }

    private VBox makeOuterBox() {
        VBox outerBox = new VBox();
        outerBox.setAlignment(Pos.CENTER);
        outerBox.setPrefWidth(400);
        outerBox.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, null, null)));
        pane.getChildren().add(outerBox);
        return outerBox;
    }

    private void makeMainPopularity() {
        Text text = new Text("Popularity : " + 0);//correct number
        text.setStyle("-fx-font: 18 arial;");
        text.setOnMouseClicked(mouseEvent -> {
            if (!popularityPopup.isShowing()) {
                popularityPopup.setAnchorX(347 + stage.getX());
                popularityPopup.setAnchorY(570 + stage.getY());
                popularityPopup.show(stage);
            } else popularityPopup.hide();
        });
        CircleImage circleImage = new CircleImage("/images/green.png", 20); //todo change
        Button button = makeButton();
        makeHbox(text, circleImage, button);
    }

    private void makeHbox(Text text, Node circleImage, Button button) {
        HBox hBox = new HBox(text, circleImage, button);
        hBox.setAlignment(Pos.CENTER);
        hBox.setTranslateX(340);
        hBox.setTranslateY(670);
        hBox.setPrefWidth(400);
        hBox.setSpacing(40);
        hBox.setBackground(new Background(new BackgroundFill(Color.GOLD, null, null)));
        pane.getChildren().add(hBox);
    }

    private static Button makeButton() {
        Button button = new Button("change factors");
        button.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px;" +
                " -fx-background-color: lightYellow; -fx-border-color: black;");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new SetFactorsMenu().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return button;
    }

    private void addVbox(HBox factorsHBox, int switcher) {
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        factorsHBox.getChildren().add(vBox);
        for (Empire.PopularityFactors factor : Empire.PopularityFactors.values()) {
            switch (switcher) {
                case 1 -> {
                    Text text = new Text(factor.name());
                    vBox.getChildren().add(text);
                }
                case 2 -> {
                    Text text = new Text(": " + 0);
                    vBox.getChildren().add(text);
                    //todo correct numbers
                }
                case 3 -> {
                    CircleImage circleImage = new CircleImage("/images/green.png", 8); //todo change
                    vBox.getChildren().add(circleImage);
                }
                case 4 -> {
                    Text text = new Text("affect --> " + 0);
                    vBox.getChildren().add(text);
                    //todo correct numbers
                }
            }
        }
    }
}
