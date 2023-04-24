package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.OtherBuildingsType;

public class OtherBuildings extends Building {
    private final OtherBuildingsType otherBuildingsType;

    public OtherBuildings(OtherBuildingsType otherBuildingsType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(otherBuildingsType.getBuildingType(), playerNumber, positionX, positionY);
        this.otherBuildingsType = otherBuildingsType;
    }
}
