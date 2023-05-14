package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

public enum AccommodationType implements BuildType {

    BIG_STONE_GATEHOUSE( // دروازه سنگی بزرگ
            new BuildingType(200, 0, new int[]{0, 20, 0, 0}, "BSGat"),
            10, "bigStoneGatehouse"
    ),
    SMALL_STONE_GATEHOUSE( //دروازه سنگی کوچک
            new BuildingType(150, 0, new int[]{0, 15, 0, 0}, "SSGat"),
            8, "smallStoneGatehouse"
    ),
    HOVEL( // خانه
            new BuildingType(100, 0, new int[]{0, 0, 6, 0}, "Hovel"),
            8, "hovel"
    ),
    MAIN_KEEP( // مقر اصلی
            new BuildingType(500, 0, new int[]{0, 0, 0, 0}, "MKeep"),
            30, "mainKeep"
    );
    private final int numberOfSettler;
    private final BuildingType buildingType;
    private final String name;
    private final int[] neededResources;

    AccommodationType(BuildingType buildingType, int numberOfSettler, String buildingName) {
        this.buildingType = buildingType;
        this.numberOfSettler = numberOfSettler;
        this.name = buildingName;
        this.neededResources = buildingType.neededResources();
        Building.addToValidBuildingNames(this, 1);
    }

    public static void enumBuilder() {
    }

    public static AccommodationType getTypeByBuildingName(String buildingName) {
        for (AccommodationType accommodationType : AccommodationType.values()) {
            if (accommodationType.name.equals(buildingName)) return accommodationType;
        }
        return null;
    }

    public int getNumberOfSettler() {
        return numberOfSettler;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public String getName() {
        return name;
    }

    public int getNeededResources(int i) {
        return neededResources[i];
    }
}
