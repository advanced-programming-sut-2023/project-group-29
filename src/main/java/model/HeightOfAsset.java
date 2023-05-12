package model;

public enum HeightOfAsset {
    UP(2),
    MIDDLE(1),
    GROUND(0);

    private int heightNumber;

    private HeightOfAsset(int heightNumber)
    {
        this.heightNumber=heightNumber;
    }

    public int getHeightNumber() {
        return heightNumber;
    }
}
