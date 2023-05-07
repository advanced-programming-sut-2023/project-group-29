package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.ResourceExtractorType;
import model.dealing.Resource;

public class ResourceExtractor extends Building {
    private final ResourceExtractorType resourceExtractorType;
    private final int rate;
    private Resource producingResource;

    public ResourceExtractor
            (ResourceExtractorType resourceExtractorType, PlayerNumber playerNumber, int positionX, int positionY) {

        super(resourceExtractorType.getBuildingType(), playerNumber, positionX, positionY);
        this.resourceExtractorType = resourceExtractorType;
        this.rate = resourceExtractorType.getRate();
        this.producingResource = resourceExtractorType.getProducingResource();
        setShowingSignInMap();
    }

    @Override
    public String getName() {
        return resourceExtractorType.getName();
    }

    @Override
    public void setShowingSignInMap() {
        showingSignInMap = resourceExtractorType.getBuildingType().abbreviation() + (getOwnerNumber().getNumber() + 1);
    }

    public void update() {
        int change = Math.min(ownerEmpire.getEmptySpace(1),rate);
        ownerEmpire.changeTradableAmount(this.producingResource, change);
        ownerEmpire.fillStorage(1,change);
    }
}
