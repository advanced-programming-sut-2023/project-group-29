package model.buildings;

public class OtherBuildings extends Building {
    private final OtherBuildingsType otherBuildingsType;

    public OtherBuildings(OtherBuildingsType otherBuildingsType) {
        super(otherBuildingsType.getBuildingType());
        this.otherBuildingsType = otherBuildingsType;
    }
}
