package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

import java.util.ArrayList;

public enum AccommodationType {
    BIG_STONE_GATEHOUSE(null, 0, "bigStoneGatehouse"),
    SMALL_STONE_GATEHOUSE(null, 0, "smallStoneGatehouse"),
    HOVEL(null, 0, "hovel");
    private final int numberOfSettler;
    private final BuildingType buildingType;
    private String name;

    AccommodationType(BuildingType buildingType, int numberOfSettler, String buildingName) {
        this.buildingType = buildingType;
        this.numberOfSettler = numberOfSettler;
        this.name = buildingName;
        Building.addToValidBuildingNames(buildingName, 1);
    }

    public static void enumBuilder() {
    }

    public static AccommodationType getAccommodationTypeByBuildingName(String buildingName) {
        for (AccommodationType accommodationType: AccommodationType.values()) {
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
}
