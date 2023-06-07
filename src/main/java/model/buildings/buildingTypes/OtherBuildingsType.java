package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.Category;

public enum OtherBuildingsType implements BuildType {
    DRAW_BRIDGE( // پل متحرک
            new BuildingType(50, 0, new int[]{0, 0, 10, 0},
                    "drawBridge", "DBrgD", Category.CASTLE)
    ), //todo jasbi draw bridge up and down
    MARKET( // فروشگاه
            new BuildingType(30, 1, new int[]{0, 0, 5, 0},
                    "market", "Mrket", Category.INDUSTRY)
    ),
    SIEGE_TENT( // چادر محاصره
            new BuildingType(10, 0, new int[]{25, 0, 0, 0},
                    "siegeTent", "STent", Category.CASTLE)
    ),
    CAGED_WAR_DOGS(//قفسه ی سگ های جنگی
            new BuildingType(30, 0, new int[]{100, 0, 10, 0},
                    "cagedWarDogs", "CWDog", Category.CASTLE)
    ),
    OX_TETHER( // افسار گاو
            new BuildingType(30, 1, new int[]{0, 0, 5, 0},
                    "oxTether", "OxTtr", Category.INDUSTRY)
    ),
    OIL_SMELTER( //کارخانه ذوب
            new BuildingType(60, 0, new int[]{0, 20, 0, 0},
                    "oilSmelter", "OlSml", Category.CASTLE)
    ),
    MOAT( //خندق
            new BuildingType(40, 0, new int[]{50, 0, 0, 0},
                    "moat", "Moat_", Category.CASTLE)
    ),
    LADDER( //نردبان
            new BuildingType(30, 0, new int[]{0, 0, 10, 0},
                    "ladder", "Lader", Category.OTHER)
    ),
    STAIR( //پله
            new BuildingType(30, 0, new int[]{0, 20, 0, 0},
                    "stair", "Stair", Category.OTHER)
    ),
    SHORT_WALL( //دیوار
            new BuildingType(70, 0, new int[]{70, 0, 0, 0},
                    "shortWall", "SWall_", Category.OTHER)
    ),
    TALL_WALL( //دیوار
            new BuildingType(100, 0, new int[]{100, 0, 0, 0},
                    "tallWall", "TWall_", Category.OTHER)
    ),
    WALL_WITH_STAIR( //دیوار با پله
            new BuildingType(130, 0, new int[]{100, 20, 0, 0},//todo category correction?
                    "wallWithStair", "WlWSr", Category.UNBUILDABLE)
    ),
    ;

    private final BuildingType buildingType;
    private final int[] neededResources;

    OtherBuildingsType(BuildingType buildingType) {
        this.buildingType = buildingType;
        this.neededResources = buildingType.neededResources();
        Building.addToValidBuildingNames(this, 3);
    }

    public static void enumBuilder() {
    }

    public static OtherBuildingsType getTypeByBuildingName(String buildingName) {
        for (OtherBuildingsType otherBuildingsType : OtherBuildingsType.values()) {
            if (otherBuildingsType.getBuildingType().name().equals(buildingName)) return otherBuildingsType;
        }
        return null;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getNeededResources(int i) {
        return neededResources[i];
    }
}
