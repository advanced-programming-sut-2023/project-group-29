package model.buildings.buildingTypes;

import model.buildings.Building;
import model.buildings.BuildingType;

public enum AttackingBuildingType {
    LOOKOUT_TOWER(null, 0, 0, "lookoutTower"),
    CIRCLE_TOWER(null, 0, 0, "circleTower"),
    PERIMETER_TOWER(null, 0, 0, "perimeterTower"),
    SQUARE_TOWER(null, 0, 0, "squareTower"),
    DEFENCE_TURRET(null, 0, 0, "defenceTurret"),
    KILLING_PIT(null, 0, 0, "killingPit"),
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
