package model.map;

import model.PlayerNumber;
import model.buildings.buildingClasses.Accommodation;
import model.buildings.buildingTypes.AccommodationType;
import model.people.humanTypes.SoldierType;

public class MapInitializer {
    private static final int DEFAULT_MAP_COUNTS = 2;
    public static Map initialize(int mapIndex)
    {
        return switch (mapIndex) {
            case 1 -> initializeFirstMap();
            case 2 -> initializeSecondMap();
            default -> null;
        };
    }

    //در داک گفته شده که یک انبار باید در کنار مقر اصلی به صورت پیش فرض ساخته شود.
    private static Map initializeFirstMap() {
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
    private static Map initializeSecondMap()
    {
        Map secondMap=new Map(200,3);
        Cell[][] cells=secondMap.getCells();

        for (int i = 1; i < cells.length; i++)
            for (int j = 1; j < cells.length; j++) {
                if(i%3==0)cells[i][j] = new Cell(CellType.PLAIN_GROUND);
                if(i%3==1)cells[i][j] = new Cell(CellType.MEADOW);
                if(i%3==2)cells[i][j] = new Cell(CellType.RIVER);

            }

        secondMap.getCells()[5][5].setBuilding(new Accommodation(AccommodationType.MAIN_KEEP, PlayerNumber.FIRST,5,5));
        secondMap.getCells()[100][5].setBuilding(new Accommodation(AccommodationType.MAIN_KEEP, PlayerNumber.SECOND,100,5));
        secondMap.getCells()[185][5].setBuilding(new Accommodation(AccommodationType.MAIN_KEEP, PlayerNumber.THIRD,185,5));

        //secondMap.dropUnit(5,6, SoldierType.SWORDSMAN.getName(), 5,1);

        //todo map should be cloned before use
        return secondMap;
    }

    public static int getDefaultMapCounts() {
        return DEFAULT_MAP_COUNTS;
    }

}
