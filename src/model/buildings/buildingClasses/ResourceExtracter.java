package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.ResourceExtracterType;

public class ResourceExtracter extends Building {
    private final ResourceExtracterType resourceExtracterType;
    private final int rate;

    public ResourceExtracter(String buildingName, PlayerNumber playerNumber, int positionX, int positionY) {
        super(ResourceExtracterType.getResourceExtracterTypeByBuildingName(buildingName).getBuildingType(), playerNumber, positionX, positionY);
        this.resourceExtracterType = ResourceExtracterType.getResourceExtracterTypeByBuildingName(buildingName);
        this.rate = resourceExtracterType.getRate();
    }
}
