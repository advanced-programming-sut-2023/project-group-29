package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

public enum StoreType {
    ARMOURY( //اسلحه خانه
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 5, 0}),
            0, "armoury"
    ),
    FOOD_STORE( //انبار غذا
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 5, 0}),
            0, "foodStore"
    ),
    STOCK_PILE( //انبار
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 0, 0}),
            0, "stockPile"
    ),
    ;

    private final int capacity;
    private final BuildingType buildingType;
    private String name;


    StoreType(BuildingType buildingType, int capacity, String buildingName) {
        this.capacity = capacity;
        this.buildingType = buildingType;
        Building.addToValidBuildingNames(buildingName, 8);
    }

    public static void enumBuilder() {
    }

    public static StoreType getTypeByBuildingName(String buildingName) {
        for (StoreType storeType : StoreType.values()) {
            if (storeType.name.equals(buildingName)) return storeType;
        }
        return null;
    }

    public int getCapacity() {
        return capacity;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public String getName() {
        return name;
    }
}
