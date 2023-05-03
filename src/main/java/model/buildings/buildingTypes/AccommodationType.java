package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

public enum AccommodationType {
    BIG_STONE_GATEHOUSE( // دروازه سنگی بزرگ
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 20, 0, 0}),
            10, "bigStoneGatehouse"
    ),
    SMALL_STONE_GATEHOUSE( //دروازه سنگی کوچک
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 15, 0, 0}),
            8, "smallStoneGatehouse"
    ),
    HOVEL( // خانه
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 6, 0}),
            8, "hovel"
    ),
    MAIN_KEEP( // مقر اصلی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 0, 0}),
            /*correction*/8, "mainKeep"
    );
    //todo user is not allowed to build main keep


    private final int numberOfSettler;
    private final BuildingType buildingType;
    private final String name;

    AccommodationType(BuildingType buildingType, int numberOfSettler, String buildingName) {
        this.buildingType = buildingType;
        this.numberOfSettler = numberOfSettler;
        this.name = buildingName;
        Building.addToValidBuildingNames(buildingName, 1);
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
}
