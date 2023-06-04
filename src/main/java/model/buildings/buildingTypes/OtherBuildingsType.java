package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.Category;

public enum OtherBuildingsType implements BuildType {
    DRAW_BRIDGE( // پل متحرک
            new BuildingType(50, 0, new int[]{0, 0, 10, 0}, "DBrgD", Category.CASTLE),
            "drawBridge"
    ),
    MARKET( // فروشگاه
            new BuildingType(30, 1, new int[]{0, 0, 5, 0}, "Mrket", Category.INDUSTRY),
            "market"
    ),
    SIEGE_TENT( // چادر محاصره
            new BuildingType(10, 0, new int[]{25, 0, 0, 0}, "STent", Category.CASTLE),
            "siegeTent"
    ),
    CAGED_WAR_DOGS(//قفسه ی سگ های جنگی
            new BuildingType(30, 0, new int[]{100, 0, 10, 0}, "CWDog", Category.CASTLE),
            "cagedWarDogs"
    ),
    OX_TETHER( // افسار گاو
            new BuildingType(30, 1, new int[]{0, 0, 5, 0}, "OxTtr", Category.INDUSTRY),
            "oxTether"
    ),
    OIL_SMELTER( //کارخانه ذوب
            new BuildingType(60, 0, new int[]{0, 20, 0, 0}, "OlSml", Category.CASTLE),
            "oilSmelter"
    ),
    MOAT( //خندق
            new BuildingType(40, 0, new int[]{50, 0, 0, 0}, "Moat_", Category.CASTLE),
            "moat"
    ),
    LADDER( //نردبان
            new BuildingType(30, 0, new int[]{0, 0, 10, 0}, "Lader", null),//todo null?
            "ladder"
    ),
    STAIR( //پله
            new BuildingType(30, 0, new int[]{0, 20, 0, 0}, "Stair", null),//todo null?
            "stair"
    ),
    SHORT_WALL( //دیوار
            new BuildingType(70, 0, new int[]{70, 0, 0, 0}, "SWall_", null), //todo null?
            "shortWall"
    ),
    TALL_WALL( //دیوار
            new BuildingType(100, 0, new int[]{100, 0, 0, 0}, "TWall_", null),//todo null?
            "tallWall"
    ),
    WALL_WITH_STAIR( //دیوار با پله
            new BuildingType(130, 0, new int[]{100, 20, 0, 0}, "WlWSr", null),//todo null?
            "wallWithStair"
    ),
    ;

    private final BuildingType buildingType;
    private final String name;
    private final int[] neededResources;

    OtherBuildingsType(BuildingType buildingType, String buildingName) {
        this.buildingType = buildingType;
        this.name = buildingName;
        this.neededResources = buildingType.neededResources();
        Building.addToValidBuildingNames(this, 3);
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
