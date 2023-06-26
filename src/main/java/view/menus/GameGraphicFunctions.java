package view.menus;

import controller.MenuNames;
import controller.menucontrollers.BuildingFunctions;
import controller.menucontrollers.GameController;
import controller.menucontrollers.MapFunctions;
import controller.menucontrollers.UnitFunctions;
import javafx.animation.PathTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.*;
import model.buildings.Building;
import model.map.Cell;
import model.people.Human;
import model.people.humanClasses.Soldier;
import model.people.humanTypes.SoldierType;
import model.unitfeatures.Movable;
import model.unitfeatures.Offensive;
import view.Command;
import view.menus.gamepopupmenus.CellDetailsWindowGraphics;
import view.messages.SelectUnitMenuMessages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameGraphicFunctions {

    private final Pane mainPane;
    private final GameData gameData;
    private GamePopUpMenus popUpMenu = null;
    private GamePopUpMenus cellDetailsPopUp=null;

    public GameGraphicFunctions(Pane mainPane) {
        this.mainPane = mainPane;
        this.gameData= GameController.getGameData();
    }

    public void attackUnits() {
        ArrayList<Offensive> attackers = new ArrayList<>();
        for (Asset asset : gameData.getSelectedUnits())
            if (asset instanceof Offensive attacker)
                attackers.add(attacker);

        if(gameData.getDestinationCellPosition()==null){
            alertMessage(Color.YELLOW,"Attacking failed","Please specify your target cell");
            return;
        }

        Pair<Integer,Integer> destination=new Pair<>(gameData.getDestinationCellPosition().first+gameData.getCornerCellIndex().first,
                gameData.getDestinationCellPosition().second+gameData.getCornerCellIndex().second);

        SelectUnitMenuMessages result= UnitFunctions.unitsAttackingCheckError(attackers, destination);
        if (result.equals(SelectUnitMenuMessages.SUCCESSFUL)) {
            int failures = UnitFunctions.makeUnitsAttacking(attackers, destination);
            alertMessage(Color.YELLOW,"Attacking results:","attack failed");
        }
        else {
            AlertWindowPane alertWindowPane = new AlertWindowPane(mainPane, Color.RED);
            alertWindowPane.addTitle("Attacking failed");
            switch (result){
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

        Pair<Integer,Integer> destination=new Pair<>(gameData.getDestinationCellPosition().first+gameData.getCornerCellIndex().first,
                gameData.getDestinationCellPosition().second+gameData.getCornerCellIndex().second);

        SelectUnitMenuMessages result= UnitFunctions.moveUnitsCheckError(movingUnits, destination);
        if (result.equals(SelectUnitMenuMessages.SUCCESSFUL)) {
            UnitFunctions.moveUnits(movingUnits);
        }
        else {
            AlertWindowPane alertWindowPane = new AlertWindowPane(mainPane, Color.RED);
            alertWindowPane.addTitle("moving failed!");
            switch (result){
                case NO_DESTINATION_SELECTED -> alertWindowPane.addText("No cell has been selected for destination!");
                case EMPTY_MOVING_UNIT_ARRAY_LIST -> alertWindowPane.addText("There is no moving unit in selected cell(s)!");
                case BAD_PLACE_TO_MOVE_ON -> alertWindowPane.addText("The Selected cell is not proper for destination of moving!");
            }
            alertWindowPane.show();
        }

    }

    public void moveAnimate(Asset asset,ArrayList<Pair<Integer, Integer>> path){
//        PathTransition movingTransition=new PathTransition();
//        movingTransition.setDuration(Duration.seconds(2));
//        movingTransition.setNode(asset);
        //todo pointive
    }

    public void engineersPourOil() {
        ArrayList<Offensive> engineersWithOil=new ArrayList<>();
        for(Asset asset:gameData.getSelectedUnits())
            if(asset instanceof Soldier soldier &&
                    soldier.getSoldierType().equals(SoldierType.ENGINEER_WITH_OIL))
                engineersWithOil.add(soldier);

        if(!Pair.notNull(gameData.getDestinationCellPosition())) {
            alertMessage(Color.RED,"Pour oil failed!","please specify the target cell");
            return;
        }


        int engineerArrayListSize=engineersWithOil.size();

        if(engineersWithOil.size()==0){
            alertMessage(Color.RED,"Pour oil failed!","There is no engineer with oil in selected cells!");
            return;
        }

        int successes= UnitFunctions.pourOil(engineersWithOil);
        int failures=engineerArrayListSize-successes;

        alertMessage(Color.YELLOW,"Pour oil results:",failures+" pouring oil failed!");
    }

    public void dropLadders() {
        ArrayList<Soldier> soldiers=new ArrayList<>();
        for(Asset asset:gameData.getSelectedUnits())
            if(asset instanceof Soldier soldier &&
                    soldier.getSoldierType().equals(SoldierType.LADDER_MAN))
                soldiers.add(soldier);

        if(soldiers.size()==0){
            alertMessage(Color.RED,"Dropping ladder failed","You have no laddermen in selected cell(s)!");
        }
        else {
            int failures = UnitFunctions.ladderMenDropLadders(soldiers);

            alertMessage(Color.YELLOW,"Dropping ladder results:",failures+" ladder dropping failed!");
        }
    }

    public void disbandUnits() {
        ArrayList<Human> humans=new ArrayList<>();
        for(Asset asset:gameData.getSelectedUnits())
            if(asset instanceof Human human)
                humans.add(human);

        if(humans.size()==0){
            alertMessage(Color.RED,"Disband failed","You have no units in selected cell(s)!");
        }
        else {
            UnitFunctions.disbandUnits(humans);

            alertMessage(Color.GREEN,"Disband results:","All units disbanded successfully");
        }
    }

    public void setStateOfUnits() throws IOException {
        ArrayList<Offensive> attackers=Offensive.getOffensivesOfUnits(gameData.getSelectedUnits());

        if(attackers.size()==0){
            alertMessage(Color.RED,"setting state failed","you have no attacking unit here");
            return;
        }

        Pane statePane=FXMLLoader.load(EnterMenu.class.getResource("/FXML/SetUnitStateWindow.fxml"));

        popUpMenu=new GamePopUpMenus(mainPane,statePane, GamePopUpMenus.PopUpType.UNIT_STATE);
        popUpMenu.makePaneCenter(750,500);

        popUpMenu.showAndWait();
    }

    public void buildEquipments() throws IOException {

        if (gameData.getEndSelectedCellsPosition() != null &&
                !gameData.getEndSelectedCellsPosition().isEqualTo(gameData.getStartSelectedCellsPosition())) {

            alertMessage(Color.RED,"Build equipment failed!","You cannot select multiple cells for building an equipment!");
            return;
        }

        Pane equipmentPane=FXMLLoader.load(EnterMenu.class.getResource("/FXML/BuildEquipmentWindow.fxml"));

        popUpMenu=new GamePopUpMenus(mainPane,equipmentPane, GamePopUpMenus.PopUpType.BUILD_EQUIPMENT);
        popUpMenu.makePaneCenter(750,500);

        popUpMenu.showAndWait();
    }

    public void addOrRemoveSelectedUnits() throws IOException {
        Pane equipmentPane=FXMLLoader.load(EnterMenu.class.getResource("/FXML/AddOrRemoveSelectedUnits.fxml"));

        popUpMenu=new GamePopUpMenus(mainPane,equipmentPane, GamePopUpMenus.PopUpType.EDIT_SELECTED_UNITS);
        popUpMenu.makePaneCenter(750,500);

        popUpMenu.showAndWait();
    }

    public void alertMessage(Color color,String title,String text) {
        AlertWindowPane alertWindowPane = new AlertWindowPane(mainPane, color);
        alertWindowPane.addTitle(title);
        alertWindowPane.addText(text);
        alertWindowPane.show();
    }

    public void dropUnit() throws IOException {
        Pane equipmentPane=FXMLLoader.load(MapMenu.class.getResource("/FXML/DropUnits.fxml"));

        popUpMenu=new GamePopUpMenus(mainPane,equipmentPane, GamePopUpMenus.PopUpType.DROP_UNITS);
        popUpMenu.makePaneCenter(750,500);

        popUpMenu.showAndWait();
    }

    public void dropBuilding() throws IOException {
        //todo complete
        Pane equipmentPane=FXMLLoader.load(MapMenu.class.getResource("/FXML/AddOrRemoveSelectedUnits.fxml"));

        popUpMenu=new GamePopUpMenus(mainPane,equipmentPane, GamePopUpMenus.PopUpType.EDIT_SELECTED_UNITS);
        popUpMenu.makePaneCenter(750,500);

        popUpMenu.showAndWait();
    }

    public void setTexture(){
        //todo handle this for texture and trees and rocks
    }

    public MenuNames run(Scanner scanner) {
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

    private void createBuilding(Matcher matcher) {

        //MapMenuMessages result = MapMenuController.buildBuilding(x, y, buildingName);
    }

    private void setBlockTexture(Matcher matcher) {
        //MapMenuController.setBlockTexture(cellType, x, y);
    }

    private void setPartOfBlockTexture(Matcher matcher) {
        //MapMenuController.setPartOfBlockTexture(cellType, x1, y1, x2, y2);
    }

    private void clear(Matcher matcher) {
        //MapMenuController.clear(x, y);
    }

    private void dropRock(Matcher matcher) {

        //System.out.println(MapMenuController.dropRock(x, y, direction));
    }

    private void dropTree(Matcher matcher) {

//        MapMenuMessages result = MapMenuController.dropTree(x, y, type);
//        switch (result) {
//            case SUCCESSFUL -> System.out.println("The tree was successfully added!");
//            case INVALID_TYPE -> System.out.println("Invalid type of tree!");
//            case INVALID_INDEX -> System.out.println("Invalid Index!");
//        }
    }

    private void dropUnit(Matcher matcher) {
//        MapMenuMessages mapMenuMessages = MapMenuController.dropUnit(x, y, type, count, playerNumberInt);
//        switch (mapMenuMessages) {
//            case INVALID_UNIT_NAME -> System.out.println("Invalid unit name!");
//            case INVALID_INDEX -> System.out.println("Invalid index!");
//            case IMPROPER_CELL_TYPE -> System.out.println("You can not drop this type of unit here!");
//            case SUCCESSFUL -> System.out.println("You dropped your unit successfully!");
//        }
    }
    private void dropBuilding(Matcher matcher) {
//        MapMenuController.dropBuildingAsAdmin(x, y, buildingName, n);
    }

    public void exitPopUp() {
        popUpMenu.hide();
    }

    public void showDetails(int x,int y){
        FXMLLoader cellDetailsPaneLoader = new FXMLLoader(EnterMenu.class.getResource("/FXML/CellDetailsWindow.fxml"));

        Pane cellDetailsPane = null;
        try {
            cellDetailsPane = cellDetailsPaneLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        cellDetailsPane.setMouseTransparent(true);

        CellDetailsWindowGraphics cellDetailsWindowGraphics = cellDetailsPaneLoader.getController();
        cellDetailsWindowGraphics.initializeText(MapFunctions.showDetails(x,y));

        cellDetailsPopUp=new GamePopUpMenus(mainPane,cellDetailsPane, GamePopUpMenus.PopUpType.CELL_DETAILS);
        cellDetailsPopUp.makePaneCenter(300,600);
        cellDetailsPopUp.showAndWait();
    }
    public void hideDetails() {
        if (cellDetailsPopUp != null && cellDetailsPopUp.getPopUpType().equals(GamePopUpMenus.PopUpType.CELL_DETAILS)) {
            cellDetailsPopUp.hide();
            cellDetailsPopUp = null;
        }
    }

    public void selectBuilding() throws IOException {
        GameData gameData = GameController.getGameData();
        if (gameData.getStartSelectedCellsPosition().isEqualTo(gameData.getEndSelectedCellsPosition())) {
            int x = gameData.getStartSelectedCellsPosition().first + gameData.getCornerCellIndex().first;
            int y = gameData.getStartSelectedCellsPosition().second + gameData.getCornerCellIndex().second;
            Cell selectedCell = gameData.getMap().getCells()[x][y];
            if (selectedCell.getBuilding() == null) {
                //todo jasbi
                System.out.println("no building");
            } else {
                BuildingFunctions.setSelectedBuilding(selectedCell.getBuilding());
                Pane selectBuildingPane = FXMLLoader.load
                        (SelectBuildingMenu.class.getResource("/FXML/selectBuildingMenu.fxml"));
                popUpMenu = new GamePopUpMenus(mainPane, selectBuildingPane, GamePopUpMenus.PopUpType.SELECT_BUILDING);
                popUpMenu.makePaneCenter(750, 500);
                popUpMenu.showAndWait();
                String name = selectedCell.getBuilding().getName();
                if (Building.getGroupNumberByBuildingName(name) != 9) {
                    //todo jasbi: selectBuildingMenu.
                }
                System.out.println(selectedCell.getBuilding().getName());
            }
        } else {
            //داده های تجمعی
            //todo jasbi
        }
    }
}
