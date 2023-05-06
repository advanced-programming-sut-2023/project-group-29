package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

public enum OtherBuildingsType {
    DRAW_BRIDGE( // پل متحرک
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 10, 0}),
            "drawBridge"
    ),
    MARKET( // فروشگاه
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 5, 0}),
            "market"
    ),
    CHURCH( // کلیسا
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{250, 0, 0, 0}),
            "church"
    ),
    CATHEDRAL( // کلیسای جامع
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{1000, 0, 0, 0}),
            "cathedral"
    ),
    SIEGE_TENT( // چادر محاصره
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{25, 0, 0, 0}),
            "siegeTent"
    ),
    CAGED_WAR_DOGS(//قفسه ی سگ های جنگی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{100, 0, 10, 0}),
            "cagedWarDogs"
    ),
    INN( //مسافرخانه
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{100, 0, 20, 0}),
            "inn"
    ),
    ;
    private final BuildingType buildingType;
    private String name;

    OtherBuildingsType(BuildingType buildingType, String buildingName) {
        this.buildingType = buildingType;
        this.name = buildingName;
        Building.addToValidBuildingNames(buildingName, 3);
    }

    public static void enumBuilder() {
    }

    public static OtherBuildingsType getTypeByBuildingName(String buildingName) {
        for (OtherBuildingsType otherBuildingsType : OtherBuildingsType.values()) {
            if (otherBuildingsType.name.equals(buildingName)) return otherBuildingsType;
        }
        return null;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public String getName() {
        return name;
    }
}
