package model.map;

public enum CellType {
    PLAIN_GROUND(ConsoleColors.YELLOW_BACKGROUND,true,true);
    //page 27 of doc
    ;
    private final ConsoleColors showingColor;
    private final boolean ableToBuildOn;
    private final boolean ableToMoveOn;

    CellType(ConsoleColors showingColor, boolean ableToBuildOn, boolean ableToMoveOn) {
        this.ableToBuildOn = ableToBuildOn;
        this.ableToMoveOn = ableToMoveOn;
        this.showingColor = showingColor;
    }

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
