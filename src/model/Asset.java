package model;

public class Asset {
    private Empire owner;
    private PlayerNumber ownerNumber;

    public Asset(PlayerNumber ownerNumber) {
        this.ownerNumber = ownerNumber;
        //TODO: set empire from number
    }

    public Empire getOwner() {
        return owner;
    }

    public PlayerNumber getOwnerNumber() {
        return ownerNumber;
    }
}
