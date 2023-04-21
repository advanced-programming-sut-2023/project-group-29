package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.AttackingBuildingType;

public class AttackingBuilding extends Building {
    private final AttackingBuildingType attackingBuildingType;
    private final int fireRange;
    private final int defendRange;

    public AttackingBuilding(String buildingName, PlayerNumber playerNumber, int positionX, int positionY) {
        super(AttackingBuildingType.getAttackingBuildingTypeByBuildingName(buildingName).getBuildingType()
                , playerNumber, positionX, positionY);
        this.attackingBuildingType = AttackingBuildingType.getAttackingBuildingTypeByBuildingName(buildingName);
        this.defendRange = attackingBuildingType.getDefendRange();
        this.fireRange = attackingBuildingType.getFireRange();
    }
}
