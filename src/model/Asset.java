package model;

public class Asset {
    private Empire ownerEmpire;
    private final PlayerNumber ownerNumber;
    private int positionX;
    private int positionY;

    public Asset(PlayerNumber ownerNumber, int positionX, int positionY) {
        this.ownerNumber = ownerNumber;
        //TODO: set empire from number
    }

    public Empire getOwnerEmpire() {
        return ownerEmpire;
    }

    public PlayerNumber getOwnerNumber() {
        return ownerNumber;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
