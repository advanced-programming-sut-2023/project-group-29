package model;

import controller.menucontrollers.GameController;
import javafx.animation.FadeTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GamePopUpMenus {
    private final Pane mainPain;
    private final Pane popUpPane;
    private final PopUpType popUpType;
    private boolean isShowing = false; //todo abbasfar: set default is true or false

    public GamePopUpMenus(Pane mainPane, Pane popUpPane, PopUpType popUpType) {
        this.mainPain = mainPane;
        this.popUpPane = popUpPane;
        this.popUpType = popUpType;
    }

    public void makePaneCenter(float width,float height) {
        popUpPane.setLayoutX(mainPain.getWidth() / 2 - width / 2);
        popUpPane.setLayoutY(mainPain.getHeight() / 2 - height / 2);
    }

    public void showAndWait() {
        mainPain.getChildren().add(popUpPane);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), popUpPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        fadeTransition.play();
        //todo debug fade transition
        GameController.getGameData().setPauseMainPane(true);
        isShowing = true;

        GameController.getGameData().setPauseMainPane(true);
    }

    //todo abbasfar fix hovering fast. starting hide animation before show complete!!!
    public void hide() {

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), popUpPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(actionEvent -> mainPain.getChildren().remove(popUpPane));

        fadeTransition.play();
        GameController.getGameData().setPauseMainPane(false);
        isShowing = false;

        GameController.getGameData().setPauseMainPane(false);
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
        BUILD_EQUIPMENT,
        BUILDING_ICONS_COLUMN,
        POPULARITY_DETAIL,
        CELL_DETAILS,
        PAUSE,
        EDIT_SELECTED_UNITS,
        DROP_UNITS,
        DROP_BUILDING,
        SET_TEXTURE,

    }
}
