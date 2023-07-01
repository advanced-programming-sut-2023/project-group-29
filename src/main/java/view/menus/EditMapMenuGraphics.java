package view.menus;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.AlertWindowPane;
import model.AppData;
import model.map.MapTemplate;
import view.Command;

import java.net.URL;

public class EditMapMenuGraphics extends Application {
    @FXML
    private Pane pane;
    @FXML
    private TextField mapNameField;
    @FXML
    private TextField width;
    @FXML
    private TextField height;
    @FXML
    private TextField playersCount;
    @FXML
    private Group publicScrollPaneContent;
    @FXML
    private Group localScrollPaneContent;

    public static void main(String[] args) {
        launch(args);
    }
    //tmp

    @Override
    public void start(Stage stage) throws Exception {

        URL url = Command.class.getResource("/FXML/EditMapMenu.fxml");
        Pane pane = FXMLLoader.load(url);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        pane.requestFocus();
        stage.show();
    }

    @FXML
    private void initialize() {
        int index = 1;
        for (MapTemplate mapTemplate : AppData.getLocalMapTemplates()) {
            HBox dataRow = new HBox();
            dataRow.setLayoutY(index * 50);
            dataRow.setMaxHeight(50);
            dataRow.setMinHeight(50);
            dataRow.setMaxWidth(540);
            dataRow.setMinWidth(540);
            if (index % 2 == 0)
                dataRow.setBackground(new Background(new BackgroundFill(Color.color(0.50, 0.45, 0.12, 1), CornerRadii.EMPTY, Insets.EMPTY)));
            else
                dataRow.setBackground(new Background(new BackgroundFill(Color.color(0.55, 0.44, 0.4, 1), CornerRadii.EMPTY, Insets.EMPTY)));

            dataRow.getChildren().add(createLabel("" + index, 20, 30));
            dataRow.getChildren().add(createLabel(mapTemplate.getName(), 50, 200));
            dataRow.getChildren().add(createLabel(mapTemplate.getCreatorUsername(), 250, 200));
            dataRow.getChildren().add(createLabel("" + mapTemplate.getWidth() + " x " + mapTemplate.getHeight(), 400, 100));
            dataRow.getChildren().add(createLabel("" + mapTemplate.getUsersCount(), 500, 30));

            localScrollPaneContent.getChildren().add(dataRow);
            index++;
        }
    }


    private Label createLabel(String text, int layoutX, int width) {
        Label label = new Label(text);
        label.setAlignment(Pos.BASELINE_CENTER);
        label.setLayoutX(layoutX);
        label.setLayoutY(0);
        label.minWidth(width);
        label.maxWidth(width);
        label.minHeight(50);
        label.maxHeight(50);
        label.setFont(new Font("Times New Roman", 20));
        return label;
    }

    public void createMap(MouseEvent mouseEvent) {
        if(AppData.getLocalMapByMapName(mapNameField.getText())!=null
                || AppData.getPublicMapByMapName(mapNameField.getText())!=null){
            makeErrorAlert("invalid name");
            return;
        }
        if(!width.getText().matches("\\d+") || !height.getText().matches("\\d+")){
            makeErrorAlert("invalid dimensions");
            return;
        }

        int widthValue=Integer.parseInt(width.getText());
        int heightValue=Integer.parseInt(height.getText());
        if(widthValue>1000 || heightValue>1000){
            makeErrorAlert("too large dimensions");
            return;
        }

        if(!playersCount.getText().matches("\\d+")){
            makeErrorAlert("invalid player counts");
            return;
        }

        int playerCountValue=Integer.parseInt(playersCount.getText());
        if(playerCountValue<2 || playerCountValue>8){
            makeErrorAlert("improper number of players");
            return;
        }

        try {
            EditMapGraphics editMapGraphics = new EditMapGraphics(new MapTemplate(AppData.getCurrentUser().getUsername(), mapNameField.getText(), widthValue, heightValue, playerCountValue));
            editMapGraphics.start(AppData.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void makeErrorAlert(String text){
        AlertWindowPane alertWindowPane=new AlertWindowPane(pane,Color.RED);
        alertWindowPane.addTitle("failed");
        alertWindowPane.addText(text);
        alertWindowPane.show();
    }

    public void back(MouseEvent mouseEvent) {
        try {
            new MainMenu().start(AppData.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
