package model;

public class Asset {
    private final PlayerNumber ownerNumber;
    protected Empire ownerEmpire;
    private int positionX;
    private int positionY;
    protected String showingSignInMap;
    //TODO add this field to all enums
    protected int hp;

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

    public int getHp()
    {
        return hp;
    }
    public void decreaseHp(int amount)
    {
        hp-=amount;
    }
    public boolean isDead()
    {
        if(hp<=0)
            return true;
        return false;
    }

    public String getShowingSignInMap()
    {
        return showingSignInMap;
    }
}
