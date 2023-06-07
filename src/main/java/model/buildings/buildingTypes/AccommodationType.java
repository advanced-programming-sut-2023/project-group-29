package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.Category;

public enum AccommodationType implements BuildType {

    BIG_STONE_GATEHOUSE( // دروازه سنگی بزرگ
            new BuildingType(200, 0, new int[]{0, 20, 0, 0},
                    "bigStoneGatehouse", "BSGat", Category.CASTLE), 10
    ),
    SMALL_STONE_GATEHOUSE( //دروازه سنگی کوچک
            new BuildingType(150, 0, new int[]{0, 15, 0, 0},
                    "smallStoneGatehouse","SSGat", Category.CASTLE), 8
    ),
    HOVEL( // خانه
            new BuildingType(100, 0, new int[]{0, 0, 6, 0},
                    "hovel", "Hovel", Category.TOWN), 8
    ),
    MAIN_KEEP( // مقر اصلی
            new BuildingType(500, 0, new int[]{0, 0, 0, 0},
                    "mainKeep", "MKeep", Category.UNBUILDABLE), 30
    );
    private final int numberOfSettler;
    private final BuildingType buildingType;
    private final int[] neededResources;

    AccommodationType(BuildingType buildingType, int numberOfSettler) {
        this.buildingType = buildingType;
        this.numberOfSettler = numberOfSettler;
        this.neededResources = buildingType.neededResources();
        Building.addToValidBuildingNames(this, 1);
    }

    public static void enumBuilder() {
    }

    public static AccommodationType getTypeByBuildingName(String buildingName) {
        for (AccommodationType accommodationType : AccommodationType.values()) {
            if (accommodationType.getBuildingType().name().equals(buildingName)) return accommodationType;
        }
        return null;
    }

    public int getNumberOfSettler() {
        return numberOfSettler;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getNeededResources(int i) {
        return neededResources[i];
    }
}
