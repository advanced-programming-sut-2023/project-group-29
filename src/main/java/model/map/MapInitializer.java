package model.map;

import controller.menucontrollers.MapMenuController;
import model.GameData;
import model.PlayerNumber;
import model.buildings.buildingClasses.Accommodation;
import model.buildings.buildingTypes.AccommodationType;
import model.people.humanTypes.SoldierType;

public class MapInitializer {
    private static final int DEFAULT_MAP_COUNTS = 2;
    public static Map initialize(int mapIndex, GameData gameData)
    {
        return switch (mapIndex) {
            case 1 -> initializeFirstMap(gameData);
            case 2 -> initializeSecondMap(gameData);
            default -> null;
        };
    }

    //در داک گفته شده که یک انبار باید در کنار مقر اصلی به صورت پیش فرض ساخته شود.
    private static Map initializeFirstMap(GameData gameData) {
        Map firstMap = new Map(100, 8);

//        secondMap.dropBuilding(5,5, AccommodationType.MAIN_KEEP.getName(),1);
//        secondMap.dropBuilding(100,5, AccommodationType.MAIN_KEEP.getName(),2);
//        secondMap.dropBuilding(185,5, AccommodationType.MAIN_KEEP.getName(),3);
//        secondMap.dropBuilding(185,100, AccommodationType.MAIN_KEEP.getName(),4);
//        secondMap.dropBuilding(185,185, AccommodationType.MAIN_KEEP.getName(),5);
//        secondMap.dropBuilding(100,185, AccommodationType.MAIN_KEEP.getName(),6);
//        secondMap.dropBuilding(5,185, AccommodationType.MAIN_KEEP.getName(),7);
//        secondMap.dropBuilding(5,100, AccommodationType.MAIN_KEEP.getName(),8);
        return null;
        //todo abbasfar
    }
    private static Map initializeSecondMap(GameData gameData)
    {
        Map secondMap=new Map(200,3);
        Cell[][] cells=secondMap.getCells();

        for (int i = 1; i < cells.length; i++)
            for (int j = 1; j < cells.length; j++) {
                cells[i][j] = new Cell(CellType.PLAIN_GROUND);
            }

        gameData.setMap(secondMap);

        MapMenuController.dropBuildingAsAdmin(5,5,AccommodationType.MAIN_KEEP.getName(), 1);
        MapMenuController.dropBuildingAsAdmin(100,5,AccommodationType.MAIN_KEEP.getName(), 2);
        MapMenuController.dropBuildingAsAdmin(185,5,AccommodationType.MAIN_KEEP.getName(), 3);

        MapMenuController.dropUnit(5,6,SoldierType.SWORDSMAN.getName(),3,1);
        MapMenuController.dropUnit(5,7,SoldierType.SWORDSMAN.getName(),3,2);
        MapMenuController.dropUnit(5,8,SoldierType.SWORDSMAN.getName(),3,3);


        return secondMap;
    }

    public static int getDefaultMapCounts() {
        return DEFAULT_MAP_COUNTS;
    }

}
