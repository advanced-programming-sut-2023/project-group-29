package view.menus;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Empire;

import java.net.URL;
import java.util.HashMap;

public class SetFactorsMenu extends Application {
    private StackPane stackPane;
    private static final HashMap<Empire.PopularityFactors, Integer> rates = new HashMap<>();

    static {
        rates.put(Empire.PopularityFactors.TAX, 0);
        rates.put(Empire.PopularityFactors.FEAR, 0);
        rates.put(Empire.PopularityFactors.FOOD, 0);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = new URL(SetFactorsMenu.class.getResource("/FXML/SetFactorMenu.fxml").toString());
        stackPane = FXMLLoader.load(url);
        stage.setScene(new Scene(stackPane));
        stage.show();
        VBox vBox = new VBox();
        stackPane.getChildren().add(vBox);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.TOP_CENTER);
        resetRateMap();
        addSliders(vBox);
    }

    private void resetRateMap() {
        for (Empire.PopularityFactors factor : Empire.PopularityFactors.values()) {
            if (factor.equals(Empire.PopularityFactors.RELIGION)) continue;
            rates.replace(factor, 0);//todo correct number
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
        vBox.getChildren().add(slider);
        slider.setMaxWidth(300);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                slider.setValue(newValue.intValue());
            }
        });
    }
}