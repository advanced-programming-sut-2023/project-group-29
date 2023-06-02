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
    private GameData gameData = null;
    private Pane mainPane=null;
    private Pane[][] tiles=null;
    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/MapMenu.fxml");
        mainPane = FXMLLoader.load(url);
        this.gameData = GameMenuController.getGameData();

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
                if(keyEvent.getCode().equals(GameKeyConstants.attackKey))
                    gameData.setGameState(GameState.ATTACKING);
                else if(keyEvent.getCode().equals(GameKeyConstants.moveKey))
                    gameData.setGameState(GameState.MOVING);
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




    public static MenuNames run(Scanner scanner) {
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();


            if ((matcher = Command.getMatcher(input, Command.SET_BLOCK_TEXTURE)) != null) {
                setBlockTexture(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.SET_PART_OF_BLOCK_TEXTURE)) != null) {
                setPartOfBlockTexture(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.CLEAR)) != null) {
                clear(matcher);
            }

            else if ((matcher = Command.getMatcher(input, Command.DROP_ROCK)) != null) {
                dropRock(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.DROP_TREE)) != null) {
                dropTree(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.DROP_BUILDING)) != null) {
                dropBuilding(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.DROP_UNIT)) != null) {
                dropUnit(matcher);
            }
        }
    }

    private static void createBuilding(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        Matcher tMatcher = Pattern.compile("-t\\s+(\\S+)").matcher(input);
        if (!(xMatcher.find() && yMatcher.find() && tMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }

        int x = Integer.parseInt(xMatcher.group(1));
        int y = Integer.parseInt(yMatcher.group(1));
        String buildingName = tMatcher.group(1);

        MapMenuMessages result = MapMenuController.buildBuilding(x, y, buildingName);
    }

    private static void setBlockTexture(Matcher matcher) {
        String input = matcher.group(0);
        Matcher typeMatcher = Pattern.compile("-t\\s+(\\S+)").matcher(input);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        if (!(typeMatcher.find() && xMatcher.find() && yMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        String type = typeMatcher.group(1);
        int trueType = 0;
        CellType cellType = null;
        for (CellType myCellType : CellType.values()) {
            if (myCellType.getName().equals(type)) {
                trueType = 1;
                cellType = myCellType;
                break;
            }
        }
        if (trueType == 0) {
            System.out.println("Invalid type!");
            return;
        }
        int x = Integer.parseInt(xMatcher.group(1));
        int y = Integer.parseInt(yMatcher.group(1));
        System.out.println(MapMenuController.setBlockTexture(cellType, x, y));
    }

    private static void setPartOfBlockTexture(Matcher matcher) {
        String input = matcher.group(0);
        Matcher typeMatcher = Pattern.compile("-t\\s+(\\S+)").matcher(input);
        Matcher x1Matcher = Pattern.compile("-x1\\s+(\\d+)").matcher(input);
        Matcher y1Matcher = Pattern.compile("-y1\\s+(\\d+)").matcher(input);
        Matcher x2Matcher = Pattern.compile("-x2\\s+(\\d+)").matcher(input);
        Matcher y2Matcher = Pattern.compile("-y2\\s+(\\d+)").matcher(input);
        if (!(typeMatcher.find() && x1Matcher.find() && y1Matcher.find() && x2Matcher.find() && y2Matcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        String type = typeMatcher.group(1);
        int trueType = 0;
        CellType cellType = null;
        for (CellType myCellType : CellType.values()) {
            if (myCellType.getName().equals(type)) {
                trueType = 1;
                cellType = myCellType;
                break;
            }
        }
        if (trueType == 0) {
            System.out.println("Invalid type!");
            return;
        }
        int x1 = Integer.parseInt(x1Matcher.group(1));
        int y1 = Integer.parseInt(y1Matcher.group(1));
        int x2 = Integer.parseInt(x2Matcher.group(1));
        int y2 = Integer.parseInt(y2Matcher.group(1));
        if (x2 < x1 || y2 < y1) {
            System.out.println("Please enter your coordinates correctly!");
            return;
        }
        System.out.println(MapMenuController.setPartOfBlockTexture(cellType, x1, y1, x2, y2));
    }

    private static void clear(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        if (!(xMatcher.find() && yMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        int x = Integer.parseInt(xMatcher.group(1));
        int y = Integer.parseInt(yMatcher.group(1));
        System.out.println(MapMenuController.clear(x, y));
    }

    private static void dropRock(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        Matcher dMatcher = Pattern.compile("-d\\s+(\\S+)").matcher(input);
        if (!(xMatcher.find() && yMatcher.find() && dMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        int x = Integer.parseInt(xMatcher.group(1));
        int y = Integer.parseInt(yMatcher.group(1));
        String direction = dMatcher.group(1);
        if (!(direction.equals("w") || direction.equals("e") || direction.equals("n") || direction.equals("s") || direction.equals("random"))) {
            System.out.println("Invalid direction!");
            return;
        }
        if (direction.equals("random")) {
            Random random = new Random();
            int myRandom = random.nextInt() % 4;
            if (myRandom == 0) {
                direction = "w";
            }
            else if (myRandom == 1) {
                direction = "e";
            }
            else if (myRandom == 2) {
                direction = "n";
            }
            else {
                direction = "s";
            }
        }
        System.out.println(MapMenuController.dropRock(x, y, direction));
    }

    private static void dropTree(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        Matcher typeMatcher = Pattern.compile("-t\\s+(\\S+)").matcher(input);
        if (!(xMatcher.find() && yMatcher.find() && typeMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        int x = Integer.parseInt(xMatcher.group(1));
        int y = Integer.parseInt(yMatcher.group(1));
        String type = typeMatcher.group(1);

        MapMenuMessages result = MapMenuController.dropTree(x, y, type);
        switch (result) {
            case SUCCESSFUL -> System.out.println("The tree was successfully added!");
            case INVALID_TYPE -> System.out.println("Invalid type of tree!");
            case INVALID_INDEX -> System.out.println("Invalid Index!");
        }
    }

    private static void dropUnit(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        Matcher cMatcher = Pattern.compile("-c\\s+(\\d+)").matcher(input);
        Matcher tMatcher = Pattern.compile("-t\\s+(\\S+)").matcher(input);
        Matcher nMatcher = Pattern.compile("-n\\s+(\\d+)").matcher(input);

        if (!(xMatcher.find() && yMatcher.find() && cMatcher.find() && tMatcher.find() && nMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        int x = Integer.parseInt(xMatcher.group(1));
        int y = Integer.parseInt(yMatcher.group(1));
        int count = Integer.parseInt(cMatcher.group(1));
        String type = tMatcher.group(1);
        int playerNumberInt = Integer.parseInt(nMatcher.group(1));
        if (count < 1) {
            System.out.println("Invalid count!");
            return;
        }
        int trueType = 0;
        for (SoldierType soldierType : SoldierType.values()) {
            if (soldierType.getName().equals(type)) {
                trueType = 1;
                break;
            }
        }
        if (trueType == 0) {
            System.out.println("Invalid type of soldier!");
            return;
        }
        MapMenuMessages mapMenuMessages = MapMenuController.dropUnit(x, y, type, count, playerNumberInt);
        switch (mapMenuMessages) {
            case INVALID_UNIT_NAME -> System.out.println("Invalid unit name!");
            case INVALID_INDEX -> System.out.println("Invalid index!");
            case IMPROPER_CELL_TYPE -> System.out.println("You can not drop this type of unit here!");
            case SUCCESSFUL -> System.out.println("You dropped your unit successfully!");
        }
    }

    private static void showDetails(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xAmount = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yAmount = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        if (!(xAmount.find() && yAmount.find())) {
            System.out.println("Invalid command!");
            return;
        }
        int positionX = Integer.parseInt(xAmount.group(1));
        int positionY = Integer.parseInt(yAmount.group(1));

        System.out.print(MapMenuController.showDetails(positionX, positionY));
    }

    private static void dropBuilding(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        Matcher tMatcher = Pattern.compile("-t\\s+(\\S+)").matcher(input);
        Matcher nMatcher = Pattern.compile("-n\\s+(\\d+)").matcher(input);
        if (!(xMatcher.find() && yMatcher.find() && tMatcher.find() && nMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }

        int x = Integer.parseInt(xMatcher.group(1));
        int y = Integer.parseInt(yMatcher.group(1));
        int n = Integer.parseInt(nMatcher.group(1));
        String buildingName = tMatcher.group(1);

        MapMenuMessages result = MapMenuController.dropBuildingAsAdmin(x, y, buildingName, n);
    }

}