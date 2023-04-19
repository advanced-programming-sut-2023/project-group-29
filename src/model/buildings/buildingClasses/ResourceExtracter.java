package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.ResourceExtracterType;

public class ResourceExtracter extends Building {
    private final ResourceExtracterType resourceExtracterType;
    private final int rate;

    public ResourceExtracter(ResourceExtracterType resourceExtracterType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(resourceExtracterType.getBuildingType(), playerNumber, positionX, positionY);
        this.resourceExtracterType = resourceExtracterType;
        this.rate = resourceExtracterType.getRate();
    }
}
