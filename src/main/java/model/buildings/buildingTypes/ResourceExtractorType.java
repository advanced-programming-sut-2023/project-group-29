package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.Category;
import model.dealing.Resource;

public enum ResourceExtractorType implements BuildType {
    PITCH_RIG( // دکل قیر
            new BuildingType(80, 1, new int[]{0, 0, 20, 0},
                    "pitchRig", "PtchR", Category.INDUSTRY), 30, Resource.PITCH
    ),
    QUARRY( // معدن سنگ
            new BuildingType(80, 3, new int[]{0, 0, 20, 0},
                    "quarry", "Quary", Category.INDUSTRY), 20, Resource.STONE
    ),
    WOOD_CUTTER( //چوب بر
            new BuildingType(30, 1, new int[]{0, 0, 3, 0},
                    "woodCutter", "WdCut", Category.INDUSTRY), 25, Resource.WOOD
    ),
    IRON_MINE(//معدن آهن
            new BuildingType(80, 2, new int[]{0, 0, 20, 0},
                    "ironMine", "IrnMn", Category.INDUSTRY), 15, Resource.IRON
    );
    //کارخانه ذوب

    private final int rate;
    private final Resource producingResource;
    private final BuildingType buildingType;
    private final int[] neededResources;


    ResourceExtractorType(BuildingType buildingType, int rate, Resource producingResource) {
        this.rate = rate;
        this.buildingType = buildingType;
        this.producingResource = producingResource;
        this.neededResources = buildingType.neededResources();
        Building.addToValidBuildingNames(this, 6);
    }

    public static void enumBuilder() {
    }

    public static ResourceExtractorType getTypeByBuildingName(String buildingName) {
        for (ResourceExtractorType resourceExtractorType : ResourceExtractorType.values()) {
            if (resourceExtractorType.getBuildingType().name().equals(buildingName)) return resourceExtractorType;
        }
        return null;
    }

    public int getRate() {
        return rate;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public Resource getProducingResource() {
        return producingResource;
    }

    public int getNeededResources(int i) {
        return neededResources[i];
    }
}
