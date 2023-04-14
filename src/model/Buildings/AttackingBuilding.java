package model.buildings;

public class AttackingBuilding extends Building {
    private AttackingBuildingType attackingBuildingType;
    private int fireRange;
    private int defendRange;

    public AttackingBuilding(AttackingBuildingType attackingBuildingType, int fireRange, int defendRange) {
        this.attackingBuildingType = attackingBuildingType;
        this.fireRange = fireRange;
        this.defendRange = defendRange;
    }
}
