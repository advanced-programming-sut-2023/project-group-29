package model.buildings;

public enum OtherBuildingsType {
    DRAW_BRIDGE(null),
    MARKET(null),
    CHURCH(null),
    CATHEDRAL(null),
    PITCH_DITCH(null),
    SIEGE_TENT(null),
    CAGED_WAR_DOGS(null),
    ;
    private final BuildingType buildingType;

    OtherBuildingsType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }
}
