package model.buildings;

public enum AccommodationType {
    BIG_STONE_GATEHOUSE(),
    SMALL_STONE_GATEHOUSE(),
    HOVEL();
    private int numberOfSettler;
    private BuildingType buildingType;

    private AccommodationType(BuildingType buildingType,int numberOfSettler)
    {
        this.buildingType=buildingType;
        this.numberOfSettler=numberOfSettler;
    }

    public int getNumberOfSettler()
    {
        return numberOfSettler;
    }

    public BuildingType getBuildingType()
    {
        return buildingType;
    }
}
