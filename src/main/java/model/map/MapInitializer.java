package model.map;

import controller.menucontrollers.MapMenuController;
import model.GameData;
import model.PlayerNumber;
import model.buildings.buildingClasses.Accommodation;
import model.buildings.buildingTypes.AccommodationType;
import model.buildings.buildingTypes.StoreType;
import model.people.humanTypes.SoldierType;

public class MapInitializer {
    private static final int DEFAULT_MAP_COUNTS = 2;
    public static Map initialize(int mapIndex, GameData gameData)
    {
        Map map;
        return switch (mapIndex) {
            case 1 -> map = initializeFirstMap(gameData);
            case 2 -> map = initializeSecondMap(gameData);
            default -> null;
        };
    }

    private static Map initializeFirstMap(GameData gameData) {
        Map firstMap = new Map(200, 8);
        if (gameData.getNumberOfPlayers() < firstMap.getUsersCount()) {
            return null;
        }
        Cell[][] cells = firstMap.getCells();
        for (int i = 1; i < cells.length; i++) {
            for (int j = 1; j < cells.length; j++) {
                if ((i + j) % 3 == 0) {
                    cells[i][j] = new Cell(CellType.PLAIN_GROUND, i, j);
                }
                if ((i + j) % 3 == 1) {
                    cells[i][j] = new Cell(CellType.GROUND_WITH_PEBBLES, i, j);
                }
                if ((i + j) % 3 == 2) {
                    cells[i][j] = new Cell(CellType.GRASS, i, j);
                }
            }
        }
        gameData.setMap(firstMap);
        MapMenuController.dropBuildingAsAdmin(25,50,AccommodationType.MAIN_KEEP.getName(), 1);
        MapMenuController.dropBuildingAsAdmin(75,50,AccommodationType.MAIN_KEEP.getName(), 2);
        MapMenuController.dropBuildingAsAdmin(125,50,AccommodationType.MAIN_KEEP.getName(), 3);
        MapMenuController.dropBuildingAsAdmin(175,50,AccommodationType.MAIN_KEEP.getName(), 4);
        MapMenuController.dropBuildingAsAdmin(25,150,AccommodationType.MAIN_KEEP.getName(), 5);
        MapMenuController.dropBuildingAsAdmin(75,150,AccommodationType.MAIN_KEEP.getName(), 6);
        MapMenuController.dropBuildingAsAdmin(125,150,AccommodationType.MAIN_KEEP.getName(), 7);
        MapMenuController.dropBuildingAsAdmin(175,150,AccommodationType.MAIN_KEEP.getName(), 8);
        MapMenuController.dropBuildingAsAdmin(26,50,StoreType.STOCK_PILE.getName(), 1);
        MapMenuController.dropBuildingAsAdmin(76,50,StoreType.STOCK_PILE.getName(), 2);
        MapMenuController.dropBuildingAsAdmin(126,50,StoreType.STOCK_PILE.getName(), 3);
        MapMenuController.dropBuildingAsAdmin(176,50,StoreType.STOCK_PILE.getName(), 4);
        MapMenuController.dropBuildingAsAdmin(26,150,StoreType.STOCK_PILE.getName(), 5);
        MapMenuController.dropBuildingAsAdmin(76,150,StoreType.STOCK_PILE.getName(), 6);
        MapMenuController.dropBuildingAsAdmin(126,150,StoreType.STOCK_PILE.getName(), 7);
        MapMenuController.dropBuildingAsAdmin(176,150,StoreType.STOCK_PILE.getName(), 8);
        return firstMap;
    }
    private static Map initializeSecondMap(GameData gameData)
    {
        Map secondMap=new Map(200,3);
        if (gameData.getNumberOfPlayers() < secondMap.getUsersCount()) {
            return null;
        }
        Cell[][] cells=secondMap.getCells();

        for (int i = 1; i < cells.length; i++)
            for (int j = 1; j < cells.length; j++) {
                if ((i % 2) == 0) {
                    cells[i][j] = new Cell(CellType.PLAIN_GROUND, i, j);
                }
                if ((i % 2) == 1) {
                    cells[i][j] = new Cell(CellType.GRASS, i, j);
                }
            }

        gameData.setMap(secondMap);

        MapMenuController.dropBuildingAsAdmin(5,10,AccommodationType.MAIN_KEEP.getName(), 1);
        MapMenuController.dropBuildingAsAdmin(100,100,AccommodationType.MAIN_KEEP.getName(), 2);
        MapMenuController.dropBuildingAsAdmin(185,190,AccommodationType.MAIN_KEEP.getName(), 3);

        MapMenuController.dropBuildingAsAdmin(6,10,StoreType.STOCK_PILE.getName(), 1);
        MapMenuController.dropBuildingAsAdmin(101,100,StoreType.STOCK_PILE.getName(), 2);
        MapMenuController.dropBuildingAsAdmin(186,190,StoreType.STOCK_PILE.getName(), 3);

        MapMenuController.dropUnit(7, 10,SoldierType.ENGINEER.getName(),2,1);
        MapMenuController.dropUnit(102, 100,SoldierType.ENGINEER.getName(),2,2);
        MapMenuController.dropUnit(187, 190,SoldierType.ENGINEER.getName(),2,3);

        MapMenuController.dropUnit(8,10,SoldierType.ARCHER.getName(),2,1);
        MapMenuController.dropUnit(103,100,SoldierType.ARCHER.getName(),2,2);
        MapMenuController.dropUnit(188,190,SoldierType.ARCHER.getName(),2,3);


        return secondMap;
    }

    public static int getDefaultMapCounts() {
        return DEFAULT_MAP_COUNTS;
    }

}
