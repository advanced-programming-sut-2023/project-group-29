package controller;

import model.*;
import model.buildings.Building;
import view.messages.SelectMenuMessages;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class SelectMenuController {
    public static void createUnit() {
    }

    public static SelectMenuMessages repairBuilding(Building building) {
        Empire ownerEmpire = building.getOwnerEmpire();
        if (!ownerEmpire.hasEnoughStoneToRepair(building)) {
            return SelectMenuMessages.LACK_OF_STONE;
        } else if (building.isEnemyNearIt()) {
            return SelectMenuMessages.ENEMY_IS_NEAR;
        }
        building.repair();
        return SelectMenuMessages.SUCCESS;
    }

    public static String moveUnit(Matcher matcher) {
        int destinationX = 1;    //TODO matcher get
        int destinationY = 1;

        GameData gameData = GameMenuController.getGameData();

        int currentX = gameData.getSelectedCellX();
        int currentY = gameData.getSelectedCellY();

        Map map = gameData.getMap();
        Cell currentCell = map.getCells()[currentX][currentY];

        //create moving object list
        ArrayList<Movable> movingObjects = new ArrayList<>();
        for (Asset movingObject : currentCell.getMovingObjects()) {
            if (movingObject.getOwnerNumber().equals(gameData.getPlayerOfTurn()))
                movingObjects.add((Movable) movingObject);
        }


        //apply moves
        int failuresCount = 0;
        ArrayList<Movable> successfullyDeletedObjects = new ArrayList<>();

        for (Movable movableObject : movingObjects) {
            Movable.MovingResult movingResult = movableObject.move(map, destinationX, destinationY);

            switch (movingResult) {
                case BAD_PLACE:
                    return "Troops cannot go there!";
                case INVALID_INDEX:
                    return "Destination index is invalid!";
                case TOO_FAR:
                    failuresCount++;
                    break;
                case SUCCESSFUL:
                    successfullyDeletedObjects.add(movableObject);
                    map.getCells()[destinationX][destinationY].getMovingObjects().add((Asset) movableObject);
                    break;
            }
        }

        for (Movable removingObject : successfullyDeletedObjects)
            currentCell.getMovingObjects().remove(removingObject);

        if (failuresCount == 0)
            return "All troops moved successfully!";

        int totalObjectCount = movingObjects.size();
        return (totalObjectCount - failuresCount) + " troop(s) out of " + totalObjectCount + " moved successfully!";
    }

    //TODO complete below
//    private static boolean distanceOfTwoCells(int firstX,int firstY,int secondX,int secondY)
//    {
//        //this function uses BFS algorithm
//
//        GameData gameData=GameMenuController.getGameData();
//        Map map= gameData.getMap();
//
//        //mark for bfs
//        int[][] mark=new int[map.getCells().length][map.getCells()[0].length];
//
//        //queue for bfs
//        class Pair{
//            public int x,y;
//        }
//        Queue<Pair> queue=new LinkedList<>();
//
//
//
//    }

    public static void patrolUnit() {
    }

    public static void setStateOfUnit() {
    }

    public static void MakeUnitAttacking() {
    }

    public static void pourOil() {
    }

    public static void digTunnel() {
    }

    public static void buildEquipment() {
    }

    public static void disbandUnit() {
    }


}
