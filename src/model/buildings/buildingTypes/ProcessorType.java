package model.buildings.buildingTypes;

import model.Resource;
import model.buildings.Building;
import model.buildings.BuildingType;

public enum ProcessorType {
    ARMOURER(0, null, null, "Armourer"),
    BLACK_SMITH(0, null, null, "blackSmith"),
    FLETCHER(0, null, null, "fletcher"),
    POLETURNER(0, null, null, "poleturner"),
    ;

    private final int productionRate;
    private final Resource resource;
    private final BuildingType buildingType;
    private String name;


    ProcessorType(int productionRate, Resource resource, BuildingType buildingType, String buildingName) {
        this.productionRate = productionRate;
        this.resource = resource;
        this.buildingType = buildingType;
        Building.addToValidBuildingNames(buildingName, 4);
    }

    public static void enumBuilder() {
    }

    public static ProcessorType getProcessorTypeByBuildingName(String buildingName) {
        for (ProcessorType processorType : ProcessorType.values()) {
            if (processorType.name.equals(buildingName)) return processorType;
        }
        return null;
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
