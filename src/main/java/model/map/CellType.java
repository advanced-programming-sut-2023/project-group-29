package model.map;

public enum CellType {
    PLAIN_GROUND(0,null,false,false);
    //LAKE, page 27 of doc
    //MOUNTAIN;
    ;

    //private int speed;
    private final ConsoleColors showingColor;
    private final boolean ableToBuildOn;
    private final boolean ableToMoveOn;

    CellType(int speed, ConsoleColors showingColor, boolean ableToBuildOn, boolean ableToMoveOn) {
        //this.speed=speed;
        this.ableToBuildOn = ableToBuildOn;
        this.ableToMoveOn = ableToMoveOn;
        this.showingColor = showingColor;
    }

//    public int getSpeed()
//    {
//        return speed;
//    }

    public ConsoleColors getShowingColor() {
        return showingColor;
    }

    public boolean isAbleToBuildOn() {
        return ableToBuildOn;
    }

    public boolean isAbleToMoveOn() {
        return ableToMoveOn;
    }
}
