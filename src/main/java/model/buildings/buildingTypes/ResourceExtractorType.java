package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.dealing.Resource;

public enum ResourceExtractorType {
    PITCH_RIG( // دکل قیر
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 20, 0}),
            0,Resource.PITCH, "pitchRig"
    ),
    //    OX_TETHER( // افسار گاو
//            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 5, 0}),
//            0, "oxTether"
//    ),
    QUARRY( // معدن سنگ
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 20, 0}),
            0,Resource.STONE, "quarry"
    ),
    WOOD_CUTTER( //چوب بر
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 3, 0}),
            0, Resource.WOOD,"woodCutter"
    ),
    IRON_MINE(//معدن آهن
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 20, 0}),
            0, Resource.IRON,"ironMine"
    );
    //کارخانه ذوب

    private int rate;
    private Resource producingResource;
    private final BuildingType buildingType;
    private String name;


    ResourceExtractorType(BuildingType buildingType, int rate, Resource producingResource, String buildingName) {
        this.rate = rate;
        this.buildingType = buildingType;
        this.producingResource = producingResource;
        Building.addToValidBuildingNames(buildingName, 6);
    }

    public static void enumBuilder() {
    }

    public static ResourceExtractorType getTypeByBuildingName(String buildingName) {
        for (ResourceExtractorType resourceExtractorType : ResourceExtractorType.values()) {
            if (resourceExtractorType.name.equals(buildingName)) return resourceExtractorType;
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

    public String getName() {
        return name;
    }
}
