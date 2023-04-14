package model.buildings;

public class Store extends Building {
    private StoreType storeType;
    private final int capacity;

    public Store(StoreType storeType) {
        super(storeType.getBuildingType());
        this.capacity = storeType.getCapacity();
    }
}
