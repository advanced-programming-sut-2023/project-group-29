package model.map;

import controller.menucontrollers.MapMenuController;
import model.buildings.buildingTypes.AccommodationType;

public enum DefaultMaps {
    FIRST_MAP(0),
    SECOND_MAP(1);

    private final int index;
    private Map map;
    private static final int defaultMapsCount=2;

    DefaultMaps(int index)
    {
        this.index=index;
        switch (index)
        {
            case 0-> this.map=initializeFirstMap();
            case 1-> this.map=initializeSecondMap();
        }
    }

    private Map initializeFirstMap()
    {
        Map firstMap=new Map(100,3);
        return null;
        //todo abbasfar
    }
    private Map initializeSecondMap()
    {
        Map secondMap=new Map(200,6);
        Cell[][] cells=secondMap.getCells();

        for(int i=1;i<cells.length;i++)
            for (int j=1;j<cells.length;j++)
            {
                cells[i][j]=new Cell(CellType.PLAIN_GROUND);
            }

        secondMap.dropBuilding(5,5, AccommodationType.MAIN_KEEP.getName(),1);
        secondMap.dropBuilding(100,5, AccommodationType.MAIN_KEEP.getName(),2);
        secondMap.dropBuilding(185,5, AccommodationType.MAIN_KEEP.getName(),3);
//        secondMap.dropBuilding(185,100, AccommodationType.MAIN_KEEP.getName(),4);
//        secondMap.dropBuilding(185,185, AccommodationType.MAIN_KEEP.getName(),5);
//        secondMap.dropBuilding(100,185, AccommodationType.MAIN_KEEP.getName(),6);
//        secondMap.dropBuilding(5,185, AccommodationType.MAIN_KEEP.getName(),7);
//        secondMap.dropBuilding(5,100, AccommodationType.MAIN_KEEP.getName(),8);


        return secondMap;
    }

    public int getIndex() {
        return index;
    }

    public Map getMap() {
        return map;
    }

    public static int getDefaultMapsCount() {
        return defaultMapsCount;
    }
}
