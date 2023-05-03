package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.dealing.Resource;

public enum ProcessorType {
    ARMOURER( // زره سازی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{100, 0, 20, 0}),
            0, null, "Armourer"
    ),
    BLACK_SMITH( // ساختمان آهنگری
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{100, 0, 20, 0}),
            0, null, "blackSmith"
    ),
    FLETCHER( // کمان سازی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{100, 0, 20, 0}),
            0, null, "fletcher"
    ),
    POLETURNER( // نیزه سازی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{100, 0, 10, 0}),
            0, null, "poleturner"
    ),
    ;

    private final int productionRate;
    private final Resource resource;
    private final BuildingType buildingType;
    private String name;


    ProcessorType(BuildingType buildingType, int productionRate, Resource resource, String buildingName) {
        this.productionRate = productionRate;
        this.resource = resource;
        this.buildingType = buildingType;
        Building.addToValidBuildingNames(buildingName, 4);
    }

    public static void enumBuilder() {
    }

    public static ProcessorType getTypeByBuildingName(String buildingName) {
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
