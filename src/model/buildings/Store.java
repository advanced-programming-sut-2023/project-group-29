package model.buildings;

public class Store extends Building {
    private StoreType storeType;
    private int capacity;

    public Store(StoreType storeType, int capacity) {
        this.storeType = storeType;
        this.capacity = capacity;
    }
}
