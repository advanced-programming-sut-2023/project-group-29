package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

public enum OtherBuildingsType {
    DRAW_BRIDGE(null, "drawBridge"),
    MARKET(null, "market"),
    CHURCH(null, "church"),
    CATHEDRAL(null, "cathedral"),
    PITCH_DITCH(null, "pitchDitch"),
    SIEGE_TENT(null, "siegeTent"),
    CAGED_WAR_DOGS(null, "cagedWarDogs"),
    ;
    private final BuildingType buildingType;
    private String name;

    OtherBuildingsType(BuildingType buildingType, String buildingName) {
        this.buildingType = buildingType;
        Building.addToValidBuildingNames(buildingName, 3);
    }

    public static void enumBuilder() {
    }

    public static OtherBuildingsType getOtherBuildingTypeByBuildingName(String buildingName) {
        for (OtherBuildingsType otherBuildingsType : OtherBuildingsType.values()) {
            if (otherBuildingsType.name.equals(buildingName)) return otherBuildingsType;
        }
        return null;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }
}
