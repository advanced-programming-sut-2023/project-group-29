package model.buildings;

public enum AccommodationType {
    BIG_STONE_GATEHOUSE(null, 0),
    SMALL_STONE_GATEHOUSE(null, 0),
    HOVEL(null, 0);
    private final int numberOfSettler;
    private final BuildingType buildingType;

    AccommodationType(BuildingType buildingType, int numberOfSettler) {
        this.buildingType = buildingType;
        this.numberOfSettler = numberOfSettler;
    }

    public int getNumberOfSettler() {
        return numberOfSettler;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }
}
