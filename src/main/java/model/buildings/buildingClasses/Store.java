package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.StoreType;

public class Store extends Building {
    private final int capacity;
    private final StoreType storeType;

    public Store(StoreType storeType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(storeType.getBuildingType(), playerNumber, positionX, positionY);
        this.storeType = storeType;
        this.capacity = storeType.getCapacity();
    }

    @Override
    public String getName() {
        return storeType.getName();
    }

}
