package model.buildings.buildingClasses;

import model.Empire;
import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.ResourceExtractorType;
import model.dealing.Resource;

public class ResourceExtractor extends Building {
    private final ResourceExtractorType resourceExtractorType;
    private final int rate;
    private final Resource producingResource;

    public ResourceExtractor
            (ResourceExtractorType resourceExtractorType, PlayerNumber playerNumber, int positionX, int positionY) {

        super(resourceExtractorType.getBuildingType(), playerNumber, positionX, positionY);
        this.resourceExtractorType = resourceExtractorType;
        this.rate = resourceExtractorType.getRate();
        this.producingResource = resourceExtractorType.getProducingResource();
        setShowingSignInMap();
    }

    @Override
    public void setShowingSignInMap() {
        showingSignInMap = resourceExtractorType.getBuildingType().abbreviation() + (getOwnerNumber().getNumber() + 1);
    }

    public void update() {
        Empire ownerEmpire = this.getOwnerEmpire();
        //exception relating quarry and ox tether!!:
        if (resourceExtractorType.equals(ResourceExtractorType.QUARRY)
                && ownerEmpire.getNumberOfBuildingType("oxTether") == 0) return;

        int change = Math.min(ownerEmpire.getEmptySpace(1), rate + ownerEmpire.getFearRate());
        ownerEmpire.changeTradableAmount(this.producingResource, change);
    }


    public int getRate() {
        return rate;
    }
}
