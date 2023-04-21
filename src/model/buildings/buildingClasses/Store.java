package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.StoreType;

public class Store extends Building {
    private final int capacity;
    private StoreType storeType;

    public Store(String buildingName, PlayerNumber playerNumber, int positionX, int positionY) {
        super(StoreType.getStoreTypeByBuildingName(buildingName).getBuildingType(), playerNumber, positionX, positionY);
        this.storeType = StoreType.getStoreTypeByBuildingName(buildingName);
        this.capacity = storeType.getCapacity();
    }
}
