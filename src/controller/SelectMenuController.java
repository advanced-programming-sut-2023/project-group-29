package controller;

import model.GameData;
import model.Map;
import model.Movable;
import model.people.Human;
import model.people.humanClasses.Soldier;
import view.GameMenu;
import view.ProfileMenu;

import java.util.*;
import java.util.regex.Matcher;

public class SelectMenuController {
    public static void createUnit() {
    }

    public static void repairBuilding(){}

    public static void moveUnit(Matcher matcher)
    {
        int destinationX=1;    //TODO matcher get
        int destinationY=1;

        GameData gameData=GameMenuController.getGameData();

        int currentX= gameData.getSelectedCellX();
        int currentY= gameData.getSelectedCellY();

        Map map= gameData.getMap();



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

    public static void patrolUnit(){}

    public static void setStateOfUnit(){}

    public static void MakeUnitAttacking(){}

    public static void pourOil(){}

    public static void digTunnel(){}

    public static void buildEquipment(){}

    public static void disbandUnit(){}


}
