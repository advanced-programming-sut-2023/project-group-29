package model.buildings.buildingClasses;

import model.Empire;
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

    //TODO ME انبارها باید متصل به هم باشند.
    //TODO ME موقع از بین رفتن انبار ظرفیت متناسب شود.
    @Override
    public String getName() {
        return storeType.getName();
    }

    public void update() {
        Empire empire = this.ownerEmpire;
        switch (this.getName()) {
            case "foodStore" -> empire.addStorage(capacity, 0);
            case "stockPile" -> empire.addStorage(capacity, 1);
            case "armoury" -> empire.addStorage(capacity, 2);
        }
    }
}
