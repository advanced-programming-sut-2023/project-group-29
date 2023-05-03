package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

public enum ServiceType {
    INN( //مسافرخانه
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{100, 0, 20, 0}),
            0, 0, "inn"
    ),
    ;

    private final int popularityRange;
    private final int wineUsage;
    private final BuildingType buildingType;
    private String name;


    ServiceType(BuildingType buildingType, int popularityRange, int wineUsage, String buildingName) {
        this.popularityRange = popularityRange;
        this.wineUsage = wineUsage;
        this.buildingType = buildingType;
        Building.addToValidBuildingNames(buildingName, 6);
    }

    public static void enumBuilder() {
    }

    public static ServiceType getTypeByBuildingName(String buildingName) {
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

    public String getName() {
        return name;
    }
}
