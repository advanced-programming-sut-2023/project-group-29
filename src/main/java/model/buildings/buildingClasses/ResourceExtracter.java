package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.ResourceExtractorType;

public class ResourceExtracter extends Building {
    private final ResourceExtractorType resourceExtractorType;
    private final int rate;

    public ResourceExtracter
            (ResourceExtractorType resourceExtractorType, PlayerNumber playerNumber, int positionX, int positionY) {

        super(resourceExtractorType.getBuildingType(), playerNumber, positionX, positionY);
        this.resourceExtractorType = resourceExtractorType;
        this.rate = resourceExtractorType.getRate();
    }
}
