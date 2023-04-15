package model.buildings.buildingTypes;

import model.buildings.BuildingType;

public enum ServiceType {
    INN(0, 0, null),
    ;

    private final int popularityRange;
    private final int wineUsage;
    private final BuildingType buildingType;

    ServiceType(int popularityRange, int wineUsage, BuildingType buildingType) {
        this.popularityRange = popularityRange;
        this.wineUsage = wineUsage;
        this.buildingType = buildingType;
    }

    public int getPopularityRange() {
        return popularityRange;
    }

    public int getWineUsage() {
        return wineUsage;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }
}
