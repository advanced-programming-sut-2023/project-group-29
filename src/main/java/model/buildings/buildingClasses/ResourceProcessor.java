package model.buildings.buildingClasses;

import model.Empire;
import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.ResourceProcessorType;
import model.dealing.Product;
import model.dealing.Resource;

public class ResourceProcessor extends Building {
    private final ResourceProcessorType resourceProcessorType;
    private final int rate;
    private final Resource resource;
    private final Product product;

    public ResourceProcessor(ResourceProcessorType resourceProcessorType,
                             PlayerNumber playerNumber, int positionX, int positionY) {
        super(resourceProcessorType.getBuildingType(), playerNumber, positionX, positionY);
        this.resourceProcessorType = resourceProcessorType;
        this.rate = resourceProcessorType.getRate();
        this.resource = resourceProcessorType.getResource();
        this.product = resourceProcessorType.getProduct();
        setShowingSignInMap();
    }

    @Override
    public String getName() {
        return resourceProcessorType.getName();
    }

    @Override
    public void setShowingSignInMap() {
            showingSignInMap = resourceProcessorType.getBuildingType().abbreviation() +
                    (getOwnerNumber().getNumber() + 1);
    }

    public void update() {
        Empire ownerEmpire=this.getOwnerEmpire();

        int availableResource = ownerEmpire.getTradableAmount(resource);
        int changeAmount = Math.min(availableResource, rate + ownerEmpire.getFearRate());
        ownerEmpire.changeTradableAmount(resource, -changeAmount);
        ownerEmpire.changeTradableAmount(product, changeAmount);
    }
}
