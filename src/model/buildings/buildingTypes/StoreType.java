package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

public enum StoreType {
    ARMOURY(0, null,"armoury"),
    FOOD_STORE(0, null, "foodStore"),
    STOCK_PILE(0, null, "stockPile"),
    ;

    private final int capacity;
    private final BuildingType buildingType;
    private String name;


    StoreType(int capacity, BuildingType buildingType, String buildingName) {
        this.capacity = capacity;
        this.buildingType = buildingType;
        Building.addToValidBuildingNames(buildingName, 7);
    }
    public static void enumBuilder() {}
    public static StoreType getStoreTypeByBuildingName(String buildingName) {
        for (StoreType storeType: StoreType.values()) {
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
}
