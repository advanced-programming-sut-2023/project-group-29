package model.buildings.buildingTypes;

import model.buildings.BuildingType;

public enum AttackingBuildingType {
    LOOKOUT_TOWER(null, 0, 0),
    CIRCLE_TOWER(null, 0, 0),
    PERIMETER_TOWER(null, 0, 0),
    SQUARE_TOWER(null, 0, 0),
    DEFENCE_TURRET(null, 0, 0),
    KILLING_PIT(null, 0, 0),
    ;

    private final int fireRange;
    private final int defendRange;
    private final BuildingType buildingType;

    AttackingBuildingType(BuildingType buildingType, int fireRange, int defendRange) {
        this.fireRange = fireRange;
        this.defendRange = defendRange;
        this.buildingType = buildingType;
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
