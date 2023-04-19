package controller;

import model.*;
import model.Map;
import model.people.Human;
import model.people.humanClasses.Soldier;
import model.weapons.Weapon;
import view.GameMenu;
import view.ProfileMenu;

import java.util.*;
import java.util.regex.Matcher;

public class SelectMenuController {
    public static void createUnit() {
    }

    public static void repairBuilding(){}

    public static String moveUnit(int destinationX,int destinationY)
    {
        GameData gameData=GameMenuController.getGameData();

        int currentX= gameData.getSelectedCellX();
        int currentY= gameData.getSelectedCellY();

        Map map= gameData.getMap();
        Cell currentCell=map.getCells()[currentX][currentY];

        //create moving object list
        ArrayList<Movable> movingObjects=currentCell.getMovingObjectsOfPlayer(gameData.getPlayerOfTurn());


        //apply moves
        int failuresCount=0;
        ArrayList<Movable> successfullyDeletedObjects=new ArrayList<>();

        for(Movable movableObject: movingObjects)
        {
            Movable.MovingResult movingResult=movableObject.move(map,destinationX,destinationY);

            switch (movingResult)
            {
                case BAD_PLACE :
                    return "Troops cannot go there!";
                case INVALID_INDEX :
                    return "Destination index is invalid!";
                case TOO_FAR :
                    failuresCount++;
                    break;
                case SUCCESSFUL:
                    successfullyDeletedObjects.add(movableObject);
                    map.getCells()[destinationX][destinationY].getMovingObjects().add((Asset)movableObject);
                    break;
            }
        }

        for(Movable removingObject:successfullyDeletedObjects)
            currentCell.getMovingObjects().remove(removingObject);

        if(failuresCount==0)
            return "All troops moved successfully!";

        int totalObjectCount=movingObjects.size();
        return (totalObjectCount-failuresCount)+" troop(s) out of "+totalObjectCount+" moved successfully!";
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

    public static void patrolUnit()
    {
        //TODO
    }

    public static void setStateOfUnit()
    {
        //TODO
    }

    public static String MakeUnitAttacking(int targetX,int targetY)
    {
        GameData gameData=GameMenuController.getGameData();

        int currentX= gameData.getSelectedCellX();
        int currentY= gameData.getSelectedCellY();

        Map map= gameData.getMap();

        //create attacking objects list
        Cell currentCell=map.getCells()[currentX][currentY];
        ArrayList<Offensive> attackingObjects=currentCell.getAttackingListOfPlayerNumber(gameData.getPlayerOfTurn());

        //create enemy objects list
        Cell targetCell=map.getCells()[targetX][targetY];
        ArrayList<Asset> enemies=targetCell.getEnemiesOfPlayerInCell(gameData.getPlayerOfTurn());

        boolean targetCellHasBuilding= targetCell.hasBuilding();
        boolean targetCellHasFiringTrap=targetCell.hasFiringTrap();
        boolean portableShieldExistsInTargetCell=targetCell.shieldExistsInCell();

        //calculate total damage

        int totalDamage=0;
        int failures=0;
        for(Offensive attackingObject:attackingObjects)
        {
            Offensive.AttackingResult attackingResult=attackingObject.attack(map,targetX,targetY);

            switch (attackingResult)
            {
                case SUCCESSFUL:
                    if(attackingObject instanceof Soldier)
                        totalDamage+=((Soldier)attackingObject).getFormulatedDamage(portableShieldExistsInTargetCell);
                    else totalDamage+=attackingObject.getDamage();
                    break;
                case INVALID_INDEX:
                    return "Your target index is invalid!";
                case TOO_FAR:
                    failures++;
                    break;
            }
        }

        //apply attack
        //TODO this is a simple implementation which decrease from every unit equally
        //if a unit is near to death or not, makes no change for others



        //apply de attack



        //results
        if(failures==0)
            return "All units attacked successfully!";

        int totalAttackingEfforts=attackingObjects.size();
        return (totalAttackingEfforts-failures)+" troops out of "+totalAttackingEfforts+" attacked successfully!";
    }

    public static void pourOil(){}

    public static void digTunnel(){}

    public static void buildEquipment(){}

    public static void disbandUnit(){}


}
