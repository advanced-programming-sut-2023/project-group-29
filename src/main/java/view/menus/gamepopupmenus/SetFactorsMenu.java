package view.menus.gamepopupmenus;

import controller.menucontrollers.GameController;
import controller.menucontrollers.MapFunctions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Empire;
import model.GameData;

import java.net.URL;
import java.util.HashMap;

public class SetFactorsMenu extends Application {
    private Stage stage;
    private StackPane stackPane;
    private static final HashMap<Empire.PopularityFactors, Integer> rates = new HashMap<>();

    private static HashMap<Empire.PopularityFactors, Slider> sliders = new HashMap<>();

    static {
        rates.put(Empire.PopularityFactors.TAX, 0);
        rates.put(Empire.PopularityFactors.FEAR, 0);
        rates.put(Empire.PopularityFactors.FOOD, 0);
    }

    public static Slider getSlider(Empire.PopularityFactors factor) {
        return sliders.get(factor);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = new URL(SetFactorsMenu.class.getResource("/FXML/SetFactorMenu.fxml").toString());
        stackPane = FXMLLoader.load(url);
        this.stage = stage;
        stage.setScene(new Scene(stackPane));
        stage.show();
        VBox vBox = new VBox();
        stackPane.getChildren().add(vBox);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.TOP_CENTER);
        resetRates();
        addSliders(vBox);
        makeSubmitButton(vBox);
    }

    private void makeSubmitButton(VBox vBox) {
        Button button = new Button("Submit");
        button.setOnMouseClicked(mouseEvent -> {
            int foodRate = (int) SetFactorsMenu.getSlider(Empire.PopularityFactors.FOOD).getValue();
            int fearRate = (int) SetFactorsMenu.getSlider(Empire.PopularityFactors.FEAR).getValue();
            int taxRate = (int) SetFactorsMenu.getSlider(Empire.PopularityFactors.TAX).getValue();
            stage.close();
            MapFunctions.setPopularityFactors(foodRate, fearRate, taxRate);
        });
        vBox.getChildren().add(button);
        button.setStyle("-fx-border-radius: 5; -fx-background-color: #cab536");
    }

    private void resetRates() {
        for (Empire.PopularityFactors factor : Empire.PopularityFactors.values()) {
            if (factor.equals(Empire.PopularityFactors.RELIGION)) continue;
            GameData gameData = GameController.getGameData();
            Empire empire = gameData.getPlayingEmpire();
            rates.replace(factor, empire.getMeasureOfFactor(factor));
        }
    }

    private void addSliders(VBox vBox) {
        Text text = new Text("Popularity factors");
        text.setStyle("-fx-font: 25 arial;");
        text.setTextAlignment(TextAlignment.CENTER);
        vBox.getChildren().add(text);
        addTitle(vBox, "food rate");
        addSlider(-2, 2, Empire.PopularityFactors.FOOD, vBox);
        addTitle(vBox, "tax rate");
        addSlider(-3, 8, Empire.PopularityFactors.TAX, vBox);
        addTitle(vBox, "fear rate");
        addSlider(-5, 5, Empire.PopularityFactors.FEAR, vBox);
    }

    private static void addTitle(VBox vBox, String title) {
        Text text2 = new Text(title);
        text2.setStyle("-fx-font: 18 arial;");
        vBox.getChildren().add(text2);
    }

    private void addSlider(int start, int end, Empire.PopularityFactors factor, VBox vBox) {
        Slider slider = new Slider(start, end, rates.get(factor));
        sliders.put(factor, slider);
        vBox.getChildren().add(slider);
        slider.setMaxWidth(300);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);
        slider.setStyle("-fx-font: 20 arial; -fx-color-label-visible: #69434e");
        slider.valueProperty().addListener((observableValue, oldValue, newValue) -> slider.setValue(newValue.intValue()));
    }
}
