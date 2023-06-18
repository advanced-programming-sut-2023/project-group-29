package model;

import controller.menucontrollers.GameController;
import javafx.animation.FadeTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GamePopUpMenus extends Pane{
    private final Pane mainPain;
    private final Pane popUpPane;
    private final PopUpType popUpType;
    private boolean isShowing = false; //todo abbasfar: set default is true or false
    public GamePopUpMenus(Pane mainPane, Pane popUpPane,PopUpType popUpType){
        this.mainPain=mainPane;
        this.popUpPane=popUpPane;
        this.popUpType=popUpType;

        setLayoutX(mainPane.getWidth()/2-popUpPane.getWidth()/2);
        setLayoutY(mainPane.getHeight()/2-popUpPane.getHeight()/2);
    }
    public void showAndWait(){
        this.setVisible(false);
        mainPain.getChildren().add(this);

        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(1),this);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(actionEvent -> this.setVisible(true));

        fadeTransition.play();
        GameController.getGameData().setPauseMainPane(true);
        isShowing = true;
    }

    //todo abbasfar fix hovering fast. starting hide animation before show complete!!!
    public void hide(){

        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(1),this);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(actionEvent -> mainPain.getChildren().remove(this));

        fadeTransition.play();
        GameController.getGameData().setPauseMainPane(false);
        isShowing = false;
    }

    public Pane getPopUpPane() {
        return popUpPane;
    }

    public PopUpType getPopUpType() {
        return popUpType;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public enum PopUpType {
        BUILDING_ICONS_COLUMN,
        POPULARITY_DETAIL,
        CELL_DETAILS,
        PAUSE,

    }
}
