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

    @Override
    public String getName() {
        return storeType.getName();
    }

    @Override
    public void setShowingSignInMap(String showingSignInMap) {
        showingSignInMap = storeType.getBuildingType().abbreviation() + getOwnerNumber().getNumber();
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
