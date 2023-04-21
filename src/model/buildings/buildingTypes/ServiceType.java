package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

public enum ServiceType {
    INN(0, 0, null, "inn"),
    ;

    private final int popularityRange;
    private final int wineUsage;
    private final BuildingType buildingType;
    private String name;


    ServiceType(int popularityRange, int wineUsage, BuildingType buildingType, String buildingName) {
        this.popularityRange = popularityRange;
        this.wineUsage = wineUsage;
        this.buildingType = buildingType;
        Building.addToValidBuildingNames(buildingName, 6);
    }

    public static void enumBuilder() {
    }

    public static ServiceType getServiceTypeByBuildingName(String buildingName) {
        for (ServiceType serviceType : ServiceType.values()) {
            if (serviceType.name.equals(buildingName)) return serviceType;
        }
        return null;
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
