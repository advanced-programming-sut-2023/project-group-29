package view.menus;

import controller.MenuNames;
import controller.menucontrollers.GameController;
import controller.menucontrollers.UnitFunctions;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.*;
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
            AlertWindowPane alertWindowPane = new AlertWindowPane(mainPane, Color.RED);
            alertWindowPane.addTitle("Attacking failed");
            alertWindowPane.addText("Please specify your target cell");
            alertWindowPane.show();
            return;
        }

        SelectUnitMenuMessages result= UnitFunctions.unitsAttackingCheckError(attackers, gameData.getDestinationCellPosition());
        if (result.equals(SelectUnitMenuMessages.SUCCESSFUL)) {
            int failures = UnitFunctions.makeUnitsAttacking(attackers, gameData.getDestinationCellPosition());
            AlertWindowPane alertWindowPane = new AlertWindowPane(mainPane, Color.YELLOW);
            alertWindowPane.addTitle("Attacking results:");
            alertWindowPane.addText(failures+"attack failed!");
            alertWindowPane.show();
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

        SelectUnitMenuMessages result= UnitFunctions.moveUnitsCheckError(movingUnits, gameData.getDestinationCellPosition());
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

        //todo abbasfar
    }

    public void engineersPourOil() {
        ArrayList<Offensive> engineersWithOil=new ArrayList<>();
        for(Asset asset:gameData.getSelectedUnits())
            if(asset instanceof Soldier soldier &&
                    soldier.getSoldierType().equals(SoldierType.ENGINEER_WITH_OIL))
                engineersWithOil.add(soldier);


        int engineerArrayListSize=engineersWithOil.size();

        if(engineersWithOil.size()==0){
            AlertWindowPane alertWindowPane = new AlertWindowPane(mainPane, Color.RED);
            alertWindowPane.addTitle("Pour oil failed!");
            alertWindowPane.addText("There is no engineer with oil in selected cells!");
            alertWindowPane.show();
            return;
        }

        int successes= UnitFunctions.pourOil(engineersWithOil);
        int failures=engineerArrayListSize-successes;

        AlertWindowPane alertWindowPane = new AlertWindowPane(mainPane, Color.YELLOW);
        alertWindowPane.addTitle("Pour oil results:");
        alertWindowPane.addText(failures+" pouring oil failed!");
        alertWindowPane.show();
    }

    public void dropLadders() {
        ArrayList<Soldier> soldiers=new ArrayList<>();
        for(Asset asset:gameData.getSelectedUnits())
            if(asset instanceof Soldier soldier &&
                    soldier.getSoldierType().equals(SoldierType.LADDER_MAN))
                soldiers.add(soldier);

        if(soldiers.size()==0){
            AlertWindowPane alertWindowPane = new AlertWindowPane(mainPane, Color.RED);
            alertWindowPane.addTitle("Dropping ladder failed");
            alertWindowPane.addText("You have no laddermen in selected cell(s)!");
            alertWindowPane.show();
        }
        else {
            int failures = UnitFunctions.ladderMenDropLadders(soldiers);

            AlertWindowPane alertWindowPane = new AlertWindowPane(mainPane, Color.YELLOW);
            alertWindowPane.addTitle("Dropping ladder results:");
            alertWindowPane.addText(failures+" ladder dropping failed!");
            alertWindowPane.show();
        }
    }

    public void disbandUnits() {
        ArrayList<Human> humans=new ArrayList<>();
        for(Asset asset:gameData.getSelectedUnits())
            if(asset instanceof Human human)
                humans.add(human);

        if(humans.size()==0){
            AlertWindowPane alertWindowPane = new AlertWindowPane(mainPane, Color.RED);
            alertWindowPane.addTitle("Disband failed");
            alertWindowPane.addText("You have no units in selected cell(s)!");
            alertWindowPane.show();
        }
        else {
            UnitFunctions.disbandUnits(humans);

            AlertWindowPane alertWindowPane = new AlertWindowPane(mainPane, Color.GREEN);
            alertWindowPane.addTitle("Disband results:");
            alertWindowPane.addText("All units disbanded successfully");
            alertWindowPane.show();
        }
    }

    public void buildEquipments() throws IOException {

        if (gameData.getEndSelectedCellsPosition() != null &&
                !gameData.getEndSelectedCellsPosition().isEqualTo(gameData.getStartSelectedCellsPosition())) {
            AlertWindowPane alertWindowPane = new AlertWindowPane(mainPane, Color.RED);
            alertWindowPane.addTitle("Build equipment failed!");
            alertWindowPane.addText("You cannot select multiple cells for building an equipment!");
            alertWindowPane.show();

            return;
        }

        Pane equipmentPane=FXMLLoader.load(MapMenu.class.getResource("/FXML/BuildEquipmentWindow.fxml"));

        popUpMenu=new GamePopUpMenus(mainPane,equipmentPane, GamePopUpMenus.PopUpType.BUILD_EQUIPMENT);
        popUpMenu.makePaneCenter(750,500);

        popUpMenu.showAndWait();
    }

    public void alertMessage(Color color,String title,String text) {
        AlertWindowPane alertWindowPane = new AlertWindowPane(mainPane, color);
        alertWindowPane.addTitle(title);
        alertWindowPane.addText(text);
        alertWindowPane.show();
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

    public void showDetail(Pane mainPane) {

        FXMLLoader cellDetailsPaneLoader = new FXMLLoader(EnterMenu.class.getResource("/FXML/CellDetailsWindow.fxml"));

        Pane cellDetailsPane = null;
        try {
            cellDetailsPane = cellDetailsPaneLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CellDetailsWindowGraphics cellDetailsWindowGraphics = cellDetailsPaneLoader.getController();

        popUpMenu = new GamePopUpMenus(mainPane, cellDetailsPane, GamePopUpMenus.PopUpType.CELL_DETAILS);
        popUpMenu.showAndWait();
    }

    public void hideDetails() {
        if (popUpMenu != null && popUpMenu.getPopUpType().equals(GamePopUpMenus.PopUpType.CELL_DETAILS)) {
            popUpMenu.hide();
            popUpMenu = null;
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

    private void showDetails(Matcher matcher) {
//        MapMenuController.showDetails(positionX, positionY));
    }

    private void dropBuilding(Matcher matcher) {
//        MapMenuController.dropBuildingAsAdmin(x, y, buildingName, n);
    }

    public void exitPopUp() {
        popUpMenu.hide();
    }
}
