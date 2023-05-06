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
    private Product product;

    public ResourceProcessor(ResourceProcessorType resourceProcessorType,
                             PlayerNumber playerNumber, int positionX, int positionY) {
        super(resourceProcessorType.getBuildingType(), playerNumber, positionX, positionY);
        this.resourceProcessorType = resourceProcessorType;
        this.rate = resourceProcessorType.getRate();
        this.resource = resourceProcessorType.getResource();
        this.product = resourceProcessorType.getProduct();
    }

    @Override
    public String getName() {
        return resourceProcessorType.getName();
    }

    @Override
    public void setShowingSignInMap(String showingSignInMap) {
            showingSignInMap = resourceProcessorType.getBuildingType().abbreviation() + getOwnerNumber().getNumber();
    }

    public void update() {
        int availableResource = ownerEmpire.getResourceAmount(this.resource);
        int changeAmount = Math.min(availableResource, rate);
        ownerEmpire.changeResourceAmount(this.resource, -changeAmount);
        ownerEmpire.changeProduct(this.product, changeAmount);
    }
}
