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

    public static int getNumberOfDefaultMaps() {
        //TODO: abbasfar complete this function;
        return 2;
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

        //MapMenuController.dropBuilding(5,5, AccommodationType.MAIN_KEEP.getNam,);
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
