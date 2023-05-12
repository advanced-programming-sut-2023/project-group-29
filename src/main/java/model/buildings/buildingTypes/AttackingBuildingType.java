package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

public enum AttackingBuildingType implements BuildType {
    LOOKOUT_TOWER( // برج دیدبانی
            new BuildingType(70, 0, new int[]{0, 10, 0, 0},"LookT"),
            30, 45, "lookoutTower"
    ),
    CIRCLE_TOWER( // برچ دایره‌ای
            new BuildingType(150, 0, new int[]{0, 40, 0, 0},"CrclT"),
            100, 150, "circleTower"
    ),
    PERIMETER_TOWER( // برج محیطی
            new BuildingType(70, 0, new int[]{0, 10, 0, 0},"PeriT"),
            30, 45, "perimeterTower"
    ),
    SQUARE_TOWER( // برج مربعی
            new BuildingType(140, 0, new int[]{0, 35, 0, 0},"SqreT"),
            90, 135, "squareTower"
    ),
    DEFENCE_TURRET( //برجک دفاعی
            new BuildingType(90, 0, new int[]{0, 15, 0, 0},"DefTr"),
            40, 60, "defenceTurret"
    ),
    ;

    private final int fireRange;
    private final int defendRange;
    private final BuildingType buildingType;
    private final String name;
    private int[] neededResources;


    AttackingBuildingType(BuildingType buildingType, int fireRange, int defendRange, String buildingName) {
        this.fireRange = fireRange;
        this.defendRange = defendRange;
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

    public int getDefendRange() {
        return defendRange;
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
