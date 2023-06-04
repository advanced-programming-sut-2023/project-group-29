package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.Category;

public enum AttackingBuildingType implements BuildType {
    LOOKOUT_TOWER( // برج دیدبانی
            new BuildingType(70, 0, new int[]{0, 10, 0, 0}, "LookT", Category.CASTLE),
            30, "lookoutTower"
    ),
    CIRCLE_TOWER( // برچ دایره‌ای
            new BuildingType(150, 0, new int[]{0, 40, 0, 0}, "CrclT", Category.CASTLE),
            100, "circleTower"
    ),
    PERIMETER_TOWER( // برج محیطی
            new BuildingType(70, 0, new int[]{0, 10, 0, 0}, "PeriT", Category.CASTLE),
            30, "perimeterTower"
    ),
    SQUARE_TOWER( // برج مربعی
            new BuildingType(140, 0, new int[]{0, 35, 0, 0}, "SqreT", Category.CASTLE),
            90, "squareTower"
    ),
    DEFENCE_TURRET( //برجک دفاعی
            new BuildingType(90, 0, new int[]{0, 15, 0, 0}, "DefTr", Category.CASTLE),
            40, "defenceTurret"
    ),
    ;

    private final int fireRange;
    private final BuildingType buildingType;
    private final String name;
    private final int[] neededResources;


    AttackingBuildingType(BuildingType buildingType, int fireRange, String buildingName) {
        this.fireRange = fireRange;
        this.buildingType = buildingType;
        this.name = buildingName;
        this.neededResources = buildingType.neededResources();
        Building.addToValidBuildingNames(this, 2);
    }

    public static void enumBuilder() {
    }

    public static AttackingBuildingType getTypeByBuildingName(String buildingName) {
        for (AttackingBuildingType attackingBuildingType : AttackingBuildingType.values()) {
            if (attackingBuildingType.name.equals(buildingName)) return attackingBuildingType;
        }
        return null;
    }

    public int getFireRange() {
        return fireRange;
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
