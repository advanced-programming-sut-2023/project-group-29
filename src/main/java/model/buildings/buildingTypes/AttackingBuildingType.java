package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

public enum AttackingBuildingType {
    LOOKOUT_TOWER( // برج دیدبانی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 10, 0, 0}),
            0, 0, "lookoutTower"
    ),
    CIRCLE_TOWER( // برچ دایره‌ای
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 40, 0, 0}),
            0, 0, "circleTower"
    ),
    PERIMETER_TOWER( // برج محیطی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 10, 0, 0}),
            0, 0, "perimeterTower"
    ),
    SQUARE_TOWER( // برج مربعی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 35, 0, 0}),
            0, 0, "squareTower"
    ),
    DEFENCE_TURRET( //برجک دفاعی
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 15, 0, 0}),
            0, 0, "defenceTurret"
    ),
    KILLING_PIT( // گودال کشتار
            new BuildingType(/*correction*/0, /*correction*/0, new int[]{0, 0, 6, 0}),
            0, 0, "killingPit"
    ),
    ;

    private final int fireRange;
    private final int defendRange;
    private final BuildingType buildingType;
    private final String name;

    AttackingBuildingType(BuildingType buildingType, int fireRange, int defendRange, String buildingName) {
        this.fireRange = fireRange;
        this.defendRange = defendRange;
        this.buildingType = buildingType;
        this.name = buildingName;
        Building.addToValidBuildingNames(buildingName, 2);
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

    public int getDefendRange() {
        return defendRange;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }
}
