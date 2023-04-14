package model.buildings;

public class AttackingBuilding extends Building {
    private final AttackingBuildingType attackingBuildingType;
    private final int fireRange;
    private final int defendRange;

    public AttackingBuilding(AttackingBuildingType attackingBuildingType) {
        super(attackingBuildingType.getBuildingType());
        this.attackingBuildingType = attackingBuildingType;
        this.defendRange = attackingBuildingType.getDefendRange();
        this.fireRange = attackingBuildingType.getFireRange();
    }
}
