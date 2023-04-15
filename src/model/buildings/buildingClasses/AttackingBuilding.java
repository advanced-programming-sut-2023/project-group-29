package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.AttackingBuildingType;

public class AttackingBuilding extends Building {
    private final AttackingBuildingType attackingBuildingType;
    private final int fireRange;
    private final int defendRange;

    public AttackingBuilding(AttackingBuildingType attackingBuildingType, PlayerNumber playerNumber) {
        super(attackingBuildingType.getBuildingType(), playerNumber);
        this.attackingBuildingType = attackingBuildingType;
        this.defendRange = attackingBuildingType.getDefendRange();
        this.fireRange = attackingBuildingType.getFireRange();
    }
}
