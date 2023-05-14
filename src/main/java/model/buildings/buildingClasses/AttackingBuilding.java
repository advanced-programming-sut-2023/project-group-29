package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.AttackingBuildingType;

public class AttackingBuilding extends Building {
    private final AttackingBuildingType attackingBuildingType;
    private final int fireRange;

    public AttackingBuilding
            (AttackingBuildingType attackingBuildingType, PlayerNumber playerNumber, int positionX, int positionY) {

        super(attackingBuildingType.getBuildingType(), playerNumber, positionX, positionY);
        this.attackingBuildingType = attackingBuildingType;
        this.fireRange = attackingBuildingType.getFireRange();
        setShowingSignInMap();
    }

    @Override
    public String getName() {
        return attackingBuildingType.getName();
    }

    @Override
    public void setShowingSignInMap() {
        showingSignInMap = attackingBuildingType.getBuildingType().abbreviation() + (getOwnerNumber().getNumber() + 1);
    }

    public int getFireRange() {
        return fireRange;
    }

    public AttackingBuildingType getAttackingBuildingType() {
        return attackingBuildingType;
    }
}
