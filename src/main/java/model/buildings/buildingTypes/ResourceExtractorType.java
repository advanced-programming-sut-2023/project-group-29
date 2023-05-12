package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.dealing.Resource;

public enum ResourceExtractorType implements BuildType {
    PITCH_RIG( // دکل قیر
            new BuildingType(80, 1, new int[]{0, 0, 20, 0},"PtchR"),
            30,Resource.PITCH, "pitchRig"
    ),
    QUARRY( // معدن سنگ
            new BuildingType(80, 3, new int[]{0, 0, 20, 0},"Quary"),
            20,Resource.STONE, "quarry"
    ),
    WOOD_CUTTER( //چوب بر
            new BuildingType(30, 1, new int[]{0, 0, 3, 0},"WdCut"),
            25, Resource.WOOD,"woodCutter"
    ),
    IRON_MINE(//معدن آهن
            new BuildingType(80, 2, new int[]{0, 0, 20, 0},"IrnMn"),
            15, Resource.IRON,"ironMine"
    );
    //کارخانه ذوب

    private int rate;
    private Resource producingResource;
    private final BuildingType buildingType;
    private String name;
    private int[] neededResources;


    ResourceExtractorType(BuildingType buildingType, int rate, Resource producingResource, String buildingName) {
        this.rate = rate;
        this.buildingType = buildingType;
        this.producingResource = producingResource;
        this.name = buildingName;
        this.neededResources = buildingType.neededResources();
        Building.addToValidBuildingNames(this, 6);
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

    public int getNeededResources(int i) {
        return neededResources[i];
    }
}
