package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.UnitCreatorType;

public class UnitCreator extends Building {
    private final int unitCost;
    private final UnitCreatorType unitCreatorType;

    public UnitCreator(UnitCreatorType unitCreatorType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(unitCreatorType.getBuildingType(), playerNumber, positionX, positionY);
        this.unitCreatorType = unitCreatorType;
        this.unitCost = unitCreatorType.getUnitCost();
        setShowingSignInMap();
    }

    public int getUnitCost() {
        return unitCost;
    }

    public UnitCreatorType getUnitCreatorType() {
        return unitCreatorType;
    }

    @Override
    public String getName() {
        return unitCreatorType.getName();
    }

    @Override
    public void setShowingSignInMap() {
        showingSignInMap = unitCreatorType.getBuildingType().abbreviation() + (getOwnerNumber().getNumber() + 1);
    }

}
