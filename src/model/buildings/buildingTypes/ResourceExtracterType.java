package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

public enum ResourceExtracterType {
    //TODO split this class
    PITCH_RIG(0, null,"pitchRig"),
    OX_TETHER(0, null, "oxTether"),
    QUARRY(0, null, "quarry"),
    WOOD_CUTTER(0, null, "woodCutter"),
    MILL(0, null, "mill"),
    IRON_MINE(0, null, "ironMine"),
    APPLE_GARDEN(0, null, "appleGarden"),
    DAIRY_PRODUCTS(0, null, "dairyProducts"),
    GRAIN_FARM(0, null, "grainFarm"),
    HUNTING_POST(0, null, "huntingPost"),
    WHEAT_FARM(0, null, "wheatFarm"),
    BAKERY(0, null, "bakery"),
    BEER_BREWING(0, null, "beerBrewing"),
    BARRACK(0, null, "barrack"),
    MERCENARY_POST(0, null, "mercenaryPost"),
    ENGINEER_GUILD(0, null, "engineerGuild"),
    STABLE(0, null, "stable"),
    ;

    private final int rate;
    private final BuildingType buildingType;
    private String name;


    ResourceExtracterType(int rate, BuildingType buildingType, String buildingName) {
        this.rate = rate;
        this.buildingType = buildingType;
        Building.addToValidBuildingNames(buildingName, 5);
    }
    public static void enumBuilder() {}
    public static ResourceExtracterType getResourceExtracterTypeByBuildingName(String buildingName) {
        for (ResourceExtracterType resourceExtracterType: ResourceExtracterType.values()) {
            if (resourceExtracterType.name.equals(buildingName)) return resourceExtracterType;
        }
        return null;
    }
    public int getRate() {
        return rate;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }
}
