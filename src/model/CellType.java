package model;

public enum CellType {
    //LAKE,
    //MOUNTAIN;
    ;

    private final int speed;
    private final Cell.Color showingColor;
    private final boolean ableToBuildOn;
    private final boolean ableToMoveOn;

    CellType(int speed, Cell.Color showingColor, boolean ableToBuildOn, boolean ableToMoveOn) {
        this.speed = speed;
        this.ableToBuildOn = ableToBuildOn;
        this.ableToMoveOn = ableToMoveOn;
        this.showingColor = showingColor;
    }

    public int getSpeed() {
        return speed;
    }

    public Cell.Color getShowingColor() {
        return showingColor;
    }

    public boolean isAbleToBuildOn() {
        return ableToBuildOn;
    }

    public boolean isAbleToMoveOn() {
        return ableToMoveOn;
    }
}
