package model;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class AlertWindowPane extends Pane {
    private final Pane mainPain;

    public AlertWindowPane(Pane mainPane, Color color) {
        this.mainPain = mainPane;
        setBackground(new Background(new BackgroundFill(color, null, null)));

        setMaxWidth(200);
        setMinWidth(200);
        setMinHeight(100);

        setLayoutX(mainPane.getWidth() / 2 - 100);
        setLayoutY(20);
    }

    public void show() {

        //fade in
        mainPain.getChildren().add(this);

        FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(1), this);
        fadeInTransition.setFromValue(0);
        fadeInTransition.setToValue(1);
        fadeInTransition.play();

        //fade out
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(1), this);
        fadeOutTransition.setFromValue(1);
        fadeOutTransition.setToValue(0);
        fadeOutTransition.setOnFinished(actionEvent -> mainPain.getChildren().remove(this));
        fadeOutTransition.setDelay(Duration.seconds(3));
        fadeOutTransition.play();
    }

    public void addTitle(String text) {
        Label label = new Label(text);
        label.setLayoutX(10);
        label.setLayoutY(10);
        this.getChildren().add(label);
    }

    public void addText(String text) {
        Label label = new Label(text);
        label.setLayoutX(10);
        label.setLayoutY(20);
        this.getChildren().add(label);
    }
}
