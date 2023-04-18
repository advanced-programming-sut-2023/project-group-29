package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.StoreType;

public class Store extends Building {
    private StoreType storeType;
    private final int capacity;

    public Store(StoreType storeType, PlayerNumber playerNumber,int positionX,int positionY) {
        super(storeType.getBuildingType(), playerNumber, positionX, positionY);
        this.capacity = storeType.getCapacity();
    }
}
