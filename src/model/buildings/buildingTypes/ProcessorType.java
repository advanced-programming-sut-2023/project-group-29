package model.buildings.buildingTypes;

import model.Resource;
import model.buildings.BuildingType;

public enum ProcessorType {
    ARMOURER(0, null, null),
    BLACK_SMITH(0, null, null),
    FLETCHER(0, null, null),
    POLETURNER(0, null, null),
    ;

    private final int productionRate;
    private final Resource resource;
    private final BuildingType buildingType;

    ProcessorType(int productionRate, Resource resource, BuildingType buildingType) {
        this.productionRate = productionRate;
        this.resource = resource;
        this.buildingType = buildingType;
    }

    public int getProductionRate() {
        return productionRate;
    }

    public Resource getMaterial() {
        return resource;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }
}
