package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

public enum OtherBuildingsType {
    DRAW_BRIDGE( // پل متحرک
            new BuildingType(50, 0, new int[]{0, 0, 10, 0},"DBrdg"),
            "drawBridge"
    ),
    //TODO: I think it should be connected to shop menu!!
    MARKET( // فروشگاه
            new BuildingType(30, 1, new int[]{0, 0, 5, 0},"Mrket"),
            "market"
    ),
    CHURCH( // کلیسا
            new BuildingType(150, 0, new int[]{250, 0, 0, 0},"Chrch"),
            "church"
    ),
    CATHEDRAL( // کلیسای جامع
            new BuildingType(300, 0, new int[]{1000, 0, 0, 0},"Ctdrl"),
            "cathedral"
    ),
    SIEGE_TENT( // چادر محاصره
            new BuildingType(10, /*TODO: JASBI: engineer and minimum*/1, new int[]{25, 0, 0, 0},"STent"),
            "siegeTent"
    ),
    CAGED_WAR_DOGS(//قفسه ی سگ های جنگی
            new BuildingType(30, 0, new int[]{100, 0, 10, 0},"CWDog"),
            "cagedWarDogs"
    ),
    INN( //مسافرخانه
            new BuildingType(120, 1, new int[]{100, 0, 20, 0},"Inn__"),
            "inn"
    ),
    MOAT( //خندق
            new BuildingType(120, 0, new int[]{50, 0, 0, 0},"Moat"),
            "moat"
    ),
    ;
    private final BuildingType buildingType;
    private String name;
    private int[] neededResources;

    OtherBuildingsType(BuildingType buildingType, String buildingName) {
        this.buildingType = buildingType;
        this.name = buildingName;
        this.neededResources = buildingType.neededResources();
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

    public int getNeededResources(int i) {
        return neededResources[i];
    }
}
