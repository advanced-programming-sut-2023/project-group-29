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
    public AlertWindowPane(Pane mainPane,Color color){
        this.mainPain=mainPane;
        setBackground(new Background(new BackgroundFill(color,null,null)));

        setMaxWidth(200);
        setMinWidth(200);
        setMinHeight(100);

        setLayoutX(mainPane.getWidth()/2-100);
        setLayoutY(20);
    }
    public void showAndWait(){
        this.setVisible(false);
        mainPain.getChildren().add(this);

        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(1),this);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(actionEvent -> this.setVisible(true));

        fadeTransition.play();
    }
    public void show(){
        //todo abbasfar complete
    }

    //todo abbasfar fix hovering fast. starting hide animation before show complete!!!
    public void hide(){

        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(1),this);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(actionEvent -> mainPain.getChildren().remove(this));

        fadeTransition.play();
    }

    public void addTitle(String text){
        Label label=new Label(text);
        label.setLayoutX(10);
        label.setLayoutY(10);
    }

    public void addText(String text){

    }
}
