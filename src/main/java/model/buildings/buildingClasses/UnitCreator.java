package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.UnitCreatorType;

public class UnitCreator extends Building {
    private int unitCost;
    private UnitCreatorType unitCreatorType;

    public UnitCreator(UnitCreatorType unitCreatorType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(unitCreatorType.getBuildingType(), playerNumber, positionX, positionY);
        this.unitCreatorType = unitCreatorType;
        this.unitCost = unitCreatorType.getUnitCost();
    }

    @Override
    public String getName() {
        return unitCreatorType.getName();
    }

}
