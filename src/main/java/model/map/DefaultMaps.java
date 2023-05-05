package model.map;

import controller.menucontrollers.MapMenuController;
import model.buildings.buildingTypes.AccommodationType;

public enum DefaultMaps {
    FIRST_MAP(0),
    SECOND_MAP(1);

    private final int index;
    private Map map;
    private static final int DEFAULT_MAP_COUNTS = DefaultMaps.values().length;

    DefaultMaps(int index) {
        this.index = index;
        switch (index) {
            case 0 -> this.map = initializeFirstMap();
            case 1 -> this.map = initializeSecondMap();
        }
    }
//در داک گفته شده که یک انبار باید در کنار مقر اصلی به صورت پیش فرض ساخته شود.
    private Map initializeFirstMap() {
        Map firstMap = new Map(100, 3);
        return null;
        //todo abbasfar
    }
    private Map initializeSecondMap()
    {
        Map secondMap=new Map(200,3);
        Cell[][] cells=secondMap.getCells();

        for (int i = 1; i < cells.length; i++)
            for (int j = 1; j < cells.length; j++) {
                cells[i][j] = new Cell(CellType.PLAIN_GROUND);
            }

        secondMap.getCells()[5][5].setBuilding(new Accommodation(AccommodationType.MAIN_KEEP, PlayerNumber.FIRST,5,5));
        secondMap.getCells()[100][5].setBuilding(new Accommodation(AccommodationType.MAIN_KEEP, PlayerNumber.SECOND,100,5));
        secondMap.getCells()[185][5].setBuilding(new Accommodation(AccommodationType.MAIN_KEEP, PlayerNumber.THIRD,185,5));

//        secondMap.dropBuilding(5,5, AccommodationType.MAIN_KEEP.getName(),1);
//        secondMap.dropBuilding(100,5, AccommodationType.MAIN_KEEP.getName(),2);
//        secondMap.dropBuilding(185,5, AccommodationType.MAIN_KEEP.getName(),3);
//        secondMap.dropBuilding(185,100, AccommodationType.MAIN_KEEP.getName(),4);
//        secondMap.dropBuilding(185,185, AccommodationType.MAIN_KEEP.getName(),5);
//        secondMap.dropBuilding(100,185, AccommodationType.MAIN_KEEP.getName(),6);
//        secondMap.dropBuilding(5,185, AccommodationType.MAIN_KEEP.getName(),7);
//        secondMap.dropBuilding(5,100, AccommodationType.MAIN_KEEP.getName(),8);

        secondMap.dropUnit(5,6, SoldierType.SWORDSMAN.getName(), 5,1);

        //todo map should be cloned before use
        return secondMap;
    }

    public int getIndex() {
        return index;
    }

    public Map getMap() {
        return map;
    }

    public static int getDefaultMapsCount() {
        return DEFAULT_MAP_COUNTS;
    }

    public static Map getMapByIndex(int index) {
        for (DefaultMaps dm : DefaultMaps.values()) {
            if (dm.index == index) return dm.map;
        }
        return null;
    }
}
