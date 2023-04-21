package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.OtherBuildingsType;

public class OtherBuildings extends Building {
    private final OtherBuildingsType otherBuildingsType;

    public OtherBuildings(String buildingName, PlayerNumber playerNumber, int positionX, int positionY) {
        super(OtherBuildingsType.getOtherBuildingTypeByBuildingName(buildingName).getBuildingType()
                , playerNumber, positionX, positionY);
        this.otherBuildingsType = OtherBuildingsType.getOtherBuildingTypeByBuildingName(buildingName);
    }
}
