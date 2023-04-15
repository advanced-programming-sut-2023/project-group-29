package model.buildings.buildingTypes;

import model.buildings.BuildingType;

public enum StoreType {
    ARMOURY(0, null),
    FOOD_STORE(0, null),
    STOCK_PILE(0, null),
    ;

    private final int capacity;
    private final BuildingType buildingType;

    StoreType(int capacity, BuildingType buildingType) {
        this.capacity = capacity;
        this.buildingType = buildingType;
    }

    public int getCapacity() {
        return capacity;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }
}
