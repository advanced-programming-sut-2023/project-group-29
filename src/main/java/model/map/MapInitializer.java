package model.map;

import controller.menucontrollers.MapFunctions;
import model.GameData;
import model.buildings.buildingTypes.AccommodationType;
import model.buildings.buildingTypes.StoreType;
import model.people.humanTypes.SoldierType;

public class MapInitializer {
    private static final int DEFAULT_MAP_COUNTS = 2;

    public static Map initialize(int mapIndex, GameData gameData) {
        return switch (mapIndex) {
            case 1 -> initializeFirstMap(gameData);
            case 2 -> initializeSecondMap(gameData);
            default -> null;
        };
    }

    private static Map initializeFirstMap(GameData gameData) {
        Map firstMap = new Map(200, 8);
        if (gameData.getNumberOfPlayers() != firstMap.getUsersCount()) {
            return null;
        }
        Cell[][] cells = firstMap.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
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
        MapFunctions.dropBuildingAsAdmin(25, 50, "mainKeep", 1);
        MapFunctions.dropBuildingAsAdmin(75, 50, "mainKeep", 2);
        MapFunctions.dropBuildingAsAdmin(125, 50, "mainKeep", 3);
        MapFunctions.dropBuildingAsAdmin(175, 50, "mainKeep", 4);
        MapFunctions.dropBuildingAsAdmin(25, 150, "mainKeep", 5);
        MapFunctions.dropBuildingAsAdmin(75, 150, "mainKeep", 6);
        MapFunctions.dropBuildingAsAdmin(125, 150, "mainKeep", 7);
        MapFunctions.dropBuildingAsAdmin(175, 150, "mainKeep", 8);
        MapFunctions.dropBuildingAsAdmin(26, 50, "stockPile", 1);
        MapFunctions.dropBuildingAsAdmin(76, 50, "stockPile", 2);
        MapFunctions.dropBuildingAsAdmin(126, 50, "stockPile", 3);
        MapFunctions.dropBuildingAsAdmin(176, 50, "stockPile", 4);
        MapFunctions.dropBuildingAsAdmin(26, 150, "stockPile", 5);
        MapFunctions.dropBuildingAsAdmin(76, 150, "stockPile", 6);
        MapFunctions.dropBuildingAsAdmin(126, 150, "stockPile", 7);
        MapFunctions.dropBuildingAsAdmin(176, 150, "stockPile", 8);
        return firstMap;
    }

    private static Map initializeSecondMap(GameData gameData) {
        Map secondMap = new Map(200, 3);
        if (gameData.getNumberOfPlayers() != secondMap.getUsersCount()) {
            return null;
        }
        Cell[][] cells = secondMap.getCells();

        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells.length; j++) {
                if ((i % 2) == 0) {
                    cells[i][j] = new Cell(CellType.PLAIN_GROUND, i, j);
                }
                if ((i % 2) == 1) {
                    cells[i][j] = new Cell(CellType.GRASS, i, j);
                }
            }

        gameData.setMap(secondMap);

        MapFunctions.dropBuildingAsAdmin(5, 10, "mainKeep", 1);
        MapFunctions.dropBuildingAsAdmin(100, 100, "mainKeep", 2);
        MapFunctions.dropBuildingAsAdmin(185, 190, "mainKeep", 3);

        MapFunctions.dropBuildingAsAdmin(6, 10, "stockPile", 1);
        MapFunctions.dropBuildingAsAdmin(101, 100, "stockPile", 2);
        MapFunctions.dropBuildingAsAdmin(186, 190, "stockPile", 3);

        MapFunctions.dropUnit(7, 10, SoldierType.ENGINEER.getName(), 2, 1);
        MapFunctions.dropUnit(102, 100, SoldierType.ENGINEER.getName(), 2, 2);
        MapFunctions.dropUnit(187, 190, SoldierType.ENGINEER.getName(), 2, 3);

        MapFunctions.dropUnit(8, 10, SoldierType.ARCHER.getName(), 2, 1);
        MapFunctions.dropUnit(103, 100, SoldierType.ARCHER.getName(), 2, 2);
        MapFunctions.dropUnit(188, 190, SoldierType.ARCHER.getName(), 2, 3);


        return secondMap;
    }

    public static int getDefaultMapCounts() {
        return DEFAULT_MAP_COUNTS;
    }

}
