package model.buildings.buildingTypes;

import model.buildings.BuildingType;

public enum ResourceExtracterType {
    //TODO split this class
    PITCH_RIG(0, null),
    OX_TETHER(0, null),
    QUARRY(0, null),
    WOOD_CUTTER(0, null),
    MILL(0, null),
    IRON_MINE(0, null),
    APPLE_GARDEN(0, null),
    DAIRY_PRODUCTS(0, null),
    GRAIN_FARM(0, null),
    HUNTING_POST(0, null),
    WHEAT_FARM(0, null),
    BAKERY(0, null),
    BEER_BREWING(0, null),
    BARRACK(0, null),
    MERCENARY_POST(0, null),
    ENGINEER_GUILD(0, null),
    STABLE(0, null),
    ;

    private final int rate;
    private final BuildingType buildingType;

    ResourceExtracterType(int rate, BuildingType buildingType) {
        this.rate = rate;
        this.buildingType = buildingType;
    }

    public int getRate() {
        return rate;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }
}
