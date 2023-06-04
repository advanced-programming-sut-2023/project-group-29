package view.menus;

import controller.MenuNames;
import controller.menucontrollers.GameMenuController;
import controller.menucontrollers.MapMenuController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.GameData;
import model.GameKeyConstants;
import model.Pair;
import model.gamestates.GameState;
import model.map.Cell;
import model.map.CellType;
import model.people.humanTypes.SoldierType;
import view.Command;
import view.messages.MapMenuMessages;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapMenu extends Application {
    private Pane mainPane=null;
    private GameData gameData = null;
    private Pane[][] tiles=null;
    private GameGraphicFunctions gameGraphicFunctions;
    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/MapMenu.fxml");
        mainPane = FXMLLoader.load(url);
        this.gameData = GameMenuController.getGameData();
        gameData.setMapMenu(this);

        gameGraphicFunctions=new GameGraphicFunctions(mainPane);
        gameData.setGameGraphicFunctions(gameGraphicFunctions);

        mainPane.requestFocus();
        setMainPaneListeners();

        setUpAndShowMap();

        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.show();
    }

    private void setUpAndShowMap(){
        tiles=MapMenuController.showMap(0, 0, mainPane);
        setTilesListeners();
    }

    private void setMainPaneListeners(){
        mainPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                keyHandle(keyEvent);
            }
        });
    }

    private void setTilesListeners(){
        for(int i=0;i<gameData.getNumberOfTilesShowingInRow();i++)
            for(int j=0;j<gameData.getNumberOfTilesShowingInColumn();j++)
            {
                int i_dup = i;
                int j_dup = j;

                tiles[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        mouseClickHandle(mouseEvent, i_dup,j_dup);
                    }
                });

                tiles[i][j].setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        mouseDragStartHandle(mouseEvent,i_dup,j_dup);
                    }
                });

                tiles[i][j].setOnMouseDragReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        mouseDragEndHandle(mouseEvent,i_dup,j_dup);
                    }
                });

                tiles[i][j].hoverProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        showDetails(i_dup,j_dup);
                    } else {
                        hideDetails(i_dup,j_dup);
                    }
                });

            }
    }

    private void showDetails(int x,int y){
        Cell cell=gameData.getMap().getCells()[x][y];

        cell.showDetail(mainPane);
    }
    private void hideDetails(int x,int y){
        Cell cell=gameData.getMap().getCells()[x][y];

        cell.hideDetails();
    }

    private void keyHandle(KeyEvent keyEvent) {
        switch (gameData.getGameState()) {
            case CELL_SELECTED -> {
                if(keyEvent.getCode().equals(GameKeyConstants.cancelKey)) {
                    gameData.setGameState(GameState.VIEW_MAP);
                    gameData.setStartSelectedCellsPosition(null);
                    gameData.setEndSelectedCellsPosition(null);
                }
                else if(keyEvent.getCode().equals(GameKeyConstants.attackKey)) {
                    gameData.setGameState(GameState.ATTACKING);
                    gameGraphicFunctions.attackUnits();
                }
                else if(keyEvent.getCode().equals(GameKeyConstants.moveKey)) {
                    gameData.setGameState(GameState.MOVING);
                    gameGraphicFunctions.moveUnits();
                }
                else if(keyEvent.getCode().equals(GameKeyConstants.pourOilKey)) {
                    gameData.setGameState(GameState.POURING_OIL);
                    gameGraphicFunctions.engineersPourOil();
                }
                else if(keyEvent.getCode().equals(GameKeyConstants.dropLadderKey)) {
                    gameGraphicFunctions.dropLadders();
                }
                else if(keyEvent.getCode().equals(GameKeyConstants.disbandUnitKey)) {
                    gameGraphicFunctions.disbandUnits();
                }
                else if(keyEvent.getCode().equals(GameKeyConstants.buildEquipmentKey)) {
                    gameGraphicFunctions.buildEquipments();
                }
            }
            case VIEW_MAP -> {
                if(keyEvent.getCode().equals(GameKeyConstants.mapMoveLeft))
                    MapMenuController.moveMap(0,0,0,1);
                else if(keyEvent.getCode().equals(GameKeyConstants.mapMoveRight))
                    MapMenuController.moveMap(0,1,0,0);
                else if(keyEvent.getCode().equals(GameKeyConstants.mapMoveDown))
                    MapMenuController.moveMap(0,0,1,0);
                else if(keyEvent.getCode().equals(GameKeyConstants.mapMoveUp))
                    MapMenuController.moveMap(1,1,0,0);

                setUpAndShowMap();
            }
        }
    }

    private void mouseClickHandle(MouseEvent mouseEvent,int tileX,int tileY) {
        switch (gameData.getGameState()) {
            case VIEW_MAP, CELL_SELECTED -> {
                gameData.setStartSelectedCellsPosition(new Pair<>(tileX,tileY));
                gameData.setEndSelectedCellsPosition(new Pair<>(tileX,tileY));
            }
        }
    }

    private void mouseDragStartHandle(MouseEvent mouseEvent,int tileX,int tileY) {
        switch (gameData.getGameState()) {
            case VIEW_MAP, CELL_SELECTED -> {
                gameData.setStartSelectedCellsPosition(new Pair<>(tileX,tileY));
                gameData.setEndSelectedCellsPosition(new Pair<>(tileX,tileY));
            }
        }
    }
    private void mouseDragEndHandle(MouseEvent mouseEvent,int tileX,int tileY) {
        switch (gameData.getGameState()) {
            case VIEW_MAP, CELL_SELECTED -> {
                gameData.setEndSelectedCellsPosition(new Pair<>(tileX,tileY));
            }
        }
    }

    public Pane getMainPane() {
        return mainPane;
    }
}