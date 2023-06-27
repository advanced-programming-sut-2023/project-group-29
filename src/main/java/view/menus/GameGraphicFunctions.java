package view.menus;

import controller.MenuNames;
import controller.menucontrollers.BuildingFunctions;
import controller.menucontrollers.GameController;
import controller.menucontrollers.MapFunctions;
import controller.menucontrollers.UnitFunctions;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.*;
import model.buildings.Building;
import model.buildings.buildingClasses.*;
import model.buildings.buildingTypes.OtherBuildingsType;
import model.map.Cell;
import model.people.Human;
import model.people.humanClasses.Soldier;
import model.people.humanTypes.SoldierType;
import model.unitfeatures.Movable;
import model.unitfeatures.Offensive;
import view.Command;
import view.menus.gamepopupmenus.CellDetailsWindowGraphics;
import view.menus.gamepopupmenus.SelectBuildingMenu;
import view.messages.MapMenuMessages;
import view.messages.SelectUnitMenuMessages;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameGraphicFunctions {

    private final Pane mainPane;
    private final GameData gameData;
    private GamePopUpMenus popUpMenu = null;
    private GamePopUpMenus cellDetailsPopUp = null;

    public GameGraphicFunctions(Pane mainPane) {
        this.mainPane = mainPane;
        this.gameData = GameController.getGameData();
    }

    public void openCreateUnitMenu() throws IOException {
        Pane createUnitPane = FXMLLoader.load(MapMenu.class.getResource("/FXML/CreateUnit.fxml"));
        popUpMenu = new GamePopUpMenus(mainPane, createUnitPane, GamePopUpMenus.PopUpType.DROP_UNITS);
        popUpMenu.makePaneCenter(750, 500);
        popUpMenu.showAndWait();
    }

    public void attackUnits() {
        popUpMenu.hide();
        ArrayList<Offensive> attackers = new ArrayList<>();
        for (Asset asset : gameData.getSelectedUnits())
            if (asset instanceof Offensive attacker)
                attackers.add(attacker);

        if (gameData.getDestinationCellPosition() == null) {
            alertMessage(Color.YELLOW, "Attacking failed", "Please specify your target cell");
            return;
        }

        Pair<Integer, Integer> destination = new Pair<>(gameData.getDestinationCellPosition().first + gameData.getCornerCellIndex().first,
                gameData.getDestinationCellPosition().second + gameData.getCornerCellIndex().second);

        SelectUnitMenuMessages result = UnitFunctions.unitsAttackingCheckError(attackers, destination);
        if (result.equals(SelectUnitMenuMessages.SUCCESSFUL)) {
            int failures = UnitFunctions.makeUnitsAttacking(attackers, destination);
            alertMessage(Color.YELLOW, "Attacking results:", "attack failed");
        }
        else {
            AlertWindowPane alertWindowPane = new AlertWindowPane(mainPane, Color.RED);
            alertWindowPane.addTitle("Attacking failed");
            switch (result) {
                case NO_ENEMY_THERE -> alertWindowPane.addText("There is no enemy in target cell");
            }
            alertWindowPane.show();
        }
    }

    public void moveUnits() {

        ArrayList<Asset> movingUnits = new ArrayList<>();
        for (Asset asset : gameData.getSelectedUnits())
            if (asset instanceof Movable)
                movingUnits.add(asset);

        Pair<Integer, Integer> destination = new Pair<>(gameData.getDestinationCellPosition().first + gameData.getCornerCellIndex().first,
                gameData.getDestinationCellPosition().second + gameData.getCornerCellIndex().second);

        SelectUnitMenuMessages result = UnitFunctions.moveUnitsCheckError(movingUnits, destination);
        if (result.equals(SelectUnitMenuMessages.SUCCESSFUL)) {
            UnitFunctions.moveUnits(movingUnits);
        }
        else {
            AlertWindowPane alertWindowPane = new AlertWindowPane(mainPane, Color.RED);
            alertWindowPane.addTitle("moving failed!");
            switch (result) {
                case NO_DESTINATION_SELECTED -> alertWindowPane.addText("No cell has been selected for destination!");
                case EMPTY_MOVING_UNIT_ARRAY_LIST ->
                        alertWindowPane.addText("There is no moving unit in selected cell(s)!");
                case BAD_PLACE_TO_MOVE_ON ->
                        alertWindowPane.addText("The Selected cell is not proper for destination of moving!");
            }
            alertWindowPane.show();
        }

    }

    public void moveAnimate(Asset asset, ArrayList<Pair<Integer, Integer>> path,int targetX,int targetY) {

        Path pathForTransition = new Path();
        if (path.size() == 0)
            return;

        float first=(path.get(0).first - gameData.getCornerCellIndex().first) * gameData.getTileWidth() + gameData.getTileWidth() / 2;
        float second=(path.get(0).second - gameData.getCornerCellIndex().second) * gameData.getTileHeight() + gameData.getTileHeight() / 2;
        pathForTransition.getElements().add(new MoveTo(first,second));

        for (Pair<Integer, Integer> pair : path) {
            float firstElement = (pair.first - gameData.getCornerCellIndex().first) * gameData.getTileWidth() + gameData.getTileWidth() / 2;
            float secondElement = (pair.second - gameData.getCornerCellIndex().second) * gameData.getTileHeight() + gameData.getTileHeight() / 2;
            pathForTransition.getElements().add(new LineTo(firstElement, secondElement));
        }

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(3));

        ImageView imageView = new ImageView(asset.getShowingImage());
        ImagePracticalFunctions.fitWidthHeight(imageView, 30, 30);
        mainPane.getChildren().add(imageView);

        pathTransition.setNode(imageView);
        pathTransition.setPath(pathForTransition);
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainPane.getChildren().remove(imageView);
                gameData.getMap().getCells()[targetX][targetY].addMovingObject(asset);
            }
        });

        pathTransition.play();
    }

    public void patrolUnits() {
        ArrayList<Movable> patrollers = new ArrayList<>();
        for (Asset asset : gameData.getSelectedUnits())
            if (asset instanceof Movable movable)
                patrollers.add(movable);

        if (!Pair.notNull(gameData.getDestinationCellPosition())) {
            alertMessage(Color.RED, "patrol failed!", "please specify the destination cell");
            return;
        }

        if (patrollers.size() == 0) {
            alertMessage(Color.RED, "patrol failed!", "There is no proper unit in selected cells!");
            return;
        }

        UnitFunctions.setUnitsPatrolling(patrollers, gameData.getDestinationCellPosition().first, gameData.getDestinationCellPosition().second);
    }

    public void digTunnel() {

        if (!Pair.notNull(gameData.getDestinationCellPosition())) {
            alertMessage(Color.RED, "digging failed!", "please specify the target cell");
            return;
        }

        Offensive tunneler = null;
        for (Asset asset : gameData.getSelectedUnits())
            if (asset instanceof Soldier soldier && soldier.getSoldierType().equals(SoldierType.TUNNELER)) {
                tunneler = soldier;
                break;
            }

        if (tunneler == null) {
            alertMessage(Color.RED, "digging failed!", "there is no tunnelers in selected units");
            return;
        }

        SelectUnitMenuMessages messages=UnitFunctions.digTunnel(tunneler,gameData.getDestinationCellPosition().first,gameData.getDestinationCellPosition().second);
        switch (messages){
            case HAS_ATTACKED -> alertMessage(Color.RED,"digging failed","please specify a not-attacked tunneler");
            case INVALID_PLACE_FOR_DIGGING_TUNNEL -> alertMessage(Color.RED,"digging failed","you can not dig tunnel there");
            case SUCCESSFUL -> alertMessage(Color.GREEN,"digging successful","tunnel was successfully digged");
        }
    }

    public void engineersPourOil() {
        ArrayList<Offensive> engineersWithOil = new ArrayList<>();
        for (Asset asset : gameData.getSelectedUnits())
            if (asset instanceof Soldier soldier &&
                    soldier.getSoldierType().equals(SoldierType.ENGINEER_WITH_OIL))
                engineersWithOil.add(soldier);

        if (!Pair.notNull(gameData.getDestinationCellPosition())) {
            alertMessage(Color.RED, "Pour oil failed!", "please specify the target cell");
            return;
        }

        int engineerArrayListSize = engineersWithOil.size();

        if (engineersWithOil.size() == 0) {
            alertMessage(Color.RED, "Pour oil failed!", "There is no engineer with oil in selected cells!");
            return;
        }

        int successes = UnitFunctions.pourOil(engineersWithOil);
        int failures = engineerArrayListSize - successes;

        alertMessage(Color.YELLOW, "Pour oil results:", failures + " pouring oil failed!");
    }

    public void dropLadders() {
        ArrayList<Soldier> soldiers = new ArrayList<>();
        for (Asset asset : gameData.getSelectedUnits())
            if (asset instanceof Soldier soldier &&
                    soldier.getSoldierType().equals(SoldierType.LADDER_MAN))
                soldiers.add(soldier);

        if (soldiers.size() == 0) {
            alertMessage(Color.RED, "Dropping ladder failed", "You have no laddermen in selected cell(s)!");
        }
        else {
            int failures = UnitFunctions.ladderMenDropLadders(soldiers);

            alertMessage(Color.YELLOW, "Dropping ladder results:", failures + " ladder dropping failed!");
        }
    }

    public void disbandUnits() {
        ArrayList<Human> humans = new ArrayList<>();
        for (Asset asset : gameData.getSelectedUnits())
            if (asset instanceof Human human)
                humans.add(human);

        if (humans.size() == 0) {
            alertMessage(Color.RED, "Disband failed", "You have no units in selected cell(s)!");
        }
        else {
            UnitFunctions.disbandUnits(humans);

            alertMessage(Color.GREEN, "Disband results:", "All units disbanded successfully");
        }
    }

    public void setStateOfUnits() throws IOException {
        ArrayList<Offensive> attackers = Offensive.getOffensivesOfUnits(gameData.getSelectedUnits());

        if (attackers.size() == 0) {
            alertMessage(Color.RED, "setting state failed", "you have no attacking unit here");
            return;
        }

        Pane statePane = FXMLLoader.load(EnterMenu.class.getResource("/FXML/SetUnitStateWindow.fxml"));

        popUpMenu = new GamePopUpMenus(mainPane, statePane, GamePopUpMenus.PopUpType.UNIT_STATE);
        popUpMenu.makePaneCenter(750, 500);

        popUpMenu.showAndWait();
    }

    public void buildEquipments() throws IOException {

        if (gameData.getEndSelectedCellsPosition() != null &&
                !gameData.getEndSelectedCellsPosition().isEqualTo(gameData.getStartSelectedCellsPosition())) {

            alertMessage(Color.RED, "Build equipment failed!", "You cannot select multiple cells for building an equipment!");
            return;
        }

        Pane equipmentPane = FXMLLoader.load(EnterMenu.class.getResource("/FXML/BuildEquipmentWindow.fxml"));

        popUpMenu = new GamePopUpMenus(mainPane, equipmentPane, GamePopUpMenus.PopUpType.BUILD_EQUIPMENT);
        popUpMenu.makePaneCenter(750, 500);

        popUpMenu.showAndWait();
    }

    public void addOrRemoveSelectedUnits() throws IOException {
        Pane equipmentPane = FXMLLoader.load(EnterMenu.class.getResource("/FXML/AddOrRemoveSelectedUnits.fxml"));

        popUpMenu = new GamePopUpMenus(mainPane, equipmentPane, GamePopUpMenus.PopUpType.EDIT_SELECTED_UNITS);
        popUpMenu.makePaneCenter(750, 500);

        popUpMenu.showAndWait();
    }

    public void alertMessage(Color color, String title, String text) {
        AlertWindowPane alertWindowPane = new AlertWindowPane(mainPane, color);
        alertWindowPane.addTitle(title);
        alertWindowPane.addText(text);
        alertWindowPane.show();
    }

    public void dropUnit() throws IOException {
        Pane equipmentPane = FXMLLoader.load(MapMenu.class.getResource("/FXML/DropUnits.fxml"));

        popUpMenu = new GamePopUpMenus(mainPane, equipmentPane, GamePopUpMenus.PopUpType.DROP_UNITS);
        popUpMenu.makePaneCenter(750, 500);

        popUpMenu.showAndWait();
    }

    public void dropBuilding() throws IOException {
        Pane equipmentPane = FXMLLoader.load(MapMenu.class.getResource("/FXML/DropBuilding.fxml"));
        popUpMenu = new GamePopUpMenus(mainPane, equipmentPane, GamePopUpMenus.PopUpType.DROP_BUILDING);
        popUpMenu.makePaneCenter(750, 500);
        popUpMenu.showAndWait();
    }

    public void setTexture() throws IOException {
        Pane equipmentPane = FXMLLoader.load(MapMenu.class.getResource("/FXML/TextureAndTree.fxml"));
        popUpMenu = new GamePopUpMenus(mainPane, equipmentPane, GamePopUpMenus.PopUpType.DROP_BUILDING);
        popUpMenu.makePaneCenter(750, 500);
        popUpMenu.showAndWait();    }


    public void exitPopUp() {
        popUpMenu.hide();
    }

    public void showDetails(int x, int y) {
        FXMLLoader cellDetailsPaneLoader = new FXMLLoader(EnterMenu.class.getResource("/FXML/CellDetailsWindow.fxml"));

        Pane cellDetailsPane = null;
        try {
            cellDetailsPane = cellDetailsPaneLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        cellDetailsPane.setMouseTransparent(true);

        CellDetailsWindowGraphics cellDetailsWindowGraphics = cellDetailsPaneLoader.getController();
        cellDetailsWindowGraphics.initializeText(MapFunctions.showDetails(x, y));

        cellDetailsPopUp = new GamePopUpMenus(mainPane, cellDetailsPane, GamePopUpMenus.PopUpType.CELL_DETAILS);
        cellDetailsPopUp.makePaneCenter(300, 600);
        cellDetailsPopUp.showAndWait();
    }

    public void hideDetails() {
        if (cellDetailsPopUp != null && cellDetailsPopUp.getPopUpType().equals(GamePopUpMenus.PopUpType.CELL_DETAILS)) {
            cellDetailsPopUp.hide();
            cellDetailsPopUp = null;
        }
    }

    public void callBuildBuilding(int x, int y, String buildingName) {
        MapMenuMessages result = MapFunctions.buildBuilding(x, y, buildingName);
        switch (result) {
            case LACK_OF_HUMAN -> alertMessage
                    (Color.RED, "lack of human", "There aren't enough humans to work in this building!");
            case LACK_OF_RESOURCES -> alertMessage
                    (Color.RED, "lack of resources", "There aren't enough resources to build this building!");
            case UNCONNECTED_STOREROOMS -> alertMessage
                    (Color.RED, "unconnected storerooms", "Your storerooms should be connected to each other");
            case TWO_MAIN_KEEP -> alertMessage
                    (Color.RED, "two main keep", "Having two main keeps is illegal!");
            case IMPROPER_CELL_TYPE -> alertMessage
                    (Color.RED, "improper cell type", "This cell isn't proper to build this building on!");
            case FULL_CELL -> alertMessage
                    (Color.RED, "full cell", "Another building has been already built here!");
            case SUCCESSFUL -> alertMessage
                    (Color.GREEN, "success", "The building was successfully built");
        }
    }

    public void selectBuilding() throws IOException {
        GameData gameData = GameController.getGameData();
        if (gameData.getStartSelectedCellsPosition().isEqualTo(gameData.getEndSelectedCellsPosition())) {
            selectOneBuilding(gameData);
        }
        else {
            selectManyBuilding(gameData);
        }
    }

    private void selectManyBuilding(GameData gameData) {
        Pair<Integer, Integer> start = gameData.getStartSelectedCellsPosition();
        Pair<Integer, Integer> end = gameData.getEndSelectedCellsPosition();
        Pair<Integer, Integer> corner = gameData.getCornerCellIndex();
        int x1 = start.first + corner.first;
        int y1 = start.second + corner.second;
        int x2 = end.first + corner.first;
        int y2 = end.second + corner.second;
        int maxProductionRate = 0, minProductionRate = 0, n = 0, sumProductionRate = 0;
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                Cell selectedCell = gameData.getMap().getCells()[i][j];
                if (selectedCell.hasBuilding()) {
                    Building building = selectedCell.getBuilding();
                    int rate;
                    if (building instanceof ProductExtractor building2) {
                        rate = building2.getRate();
                    }
                    else if (building instanceof ProductProcessor building2) {
                        rate = building2.getRate();
                    }
                    else if (building instanceof ResourceExtractor building2) {
                        rate = building2.getRate();
                    }
                    else if (building instanceof ResourceProcessor building2) {
                        rate = building2.getRate();
                    }
                    else continue;
                    if (rate > maxProductionRate) maxProductionRate = rate;
                    if (rate < minProductionRate || minProductionRate == 0) minProductionRate = rate;
                    n++;
                    sumProductionRate += rate;
                }
            }
        }
        float averageProductionRate = 0;
        if (n != 0) averageProductionRate = (float) sumProductionRate / n;
        averageProductionRate = (float) ((int) (averageProductionRate * 100)) / 100;
        show(maxProductionRate, minProductionRate, averageProductionRate);
    }

    private void show(int maxProductionRate, int minProductionRate, float averageProductionRate) {
        String maxText = "max production rate: " + maxProductionRate;
        String minText = "min production rate: " + minProductionRate;
        String averageText = "average production rate: " + averageProductionRate;
        String text = "\n" + maxText + "\n" + minText + "\n" + averageText;
        alertMessage(Color.WHITE, "rate information", text);
    }

    private void selectOneBuilding(GameData gameData) throws IOException {
        int x = gameData.getStartSelectedCellsPosition().first + gameData.getCornerCellIndex().first;
        int y = gameData.getStartSelectedCellsPosition().second + gameData.getCornerCellIndex().second;
        Cell selectedCell = gameData.getMap().getCells()[x][y];
        if (selectedCell.getBuilding() == null) {
            alertMessage(Color.YELLOW, "No building", "There is no building in the cell chosen!");
        }
        else {
            BuildingFunctions.setSelectedBuilding(selectedCell.getBuilding());
            Pane selectBuildingPane = FXMLLoader.load
                    (SelectBuildingMenu.class.getResource("/FXML/SelectBuildingMenu.fxml"));
            popUpMenu = new GamePopUpMenus(mainPane, selectBuildingPane, GamePopUpMenus.PopUpType.SELECT_BUILDING);
            popUpMenu.makePaneCenter(750, 500);
            popUpMenu.showAndWait();
        }
    }

    public void pasteBuilding() {
        try {
            PrintStream errStream = System.err;
            System.setErr(new PrintStream("error.log"));
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            String buildingName = c.getData(DataFlavor.stringFlavor).toString();
            int x = gameData.getStartSelectedCellsPosition().first + gameData.getCornerCellIndex().first;
            int y = gameData.getStartSelectedCellsPosition().second + gameData.getCornerCellIndex().second;
            if (Building.isBuildingNameValid(buildingName)) {
                callBuildBuilding(x, y, buildingName);
            }
            else {
                alertMessage(Color.RED, "false building name",
                        "The sequence in your clipboard is not name of a building");
            }
            System.setErr(errStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAttackPopUp() {
        Pane pane = new Pane();
        pane.setPrefWidth(100);
        pane.setPrefHeight(50);
        pane.getChildren().add(new Label("attacking..."));

        popUpMenu = new GamePopUpMenus(mainPane, pane, GamePopUpMenus.PopUpType.ATTACKING);
        pane.setLayoutX(500);
        pane.setLayoutY(50);
        pane.setMouseTransparent(true);
        pane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        popUpMenu.showAndWait();
    }
}
