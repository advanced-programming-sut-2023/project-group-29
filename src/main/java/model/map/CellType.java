package model.map;

public enum CellType {
    PLAIN_GROUND(ConsoleColors.YELLOW_BACKGROUND,true,true),
    GROUND_WITH_PEBBLES(ConsoleColors.CYAN_BACKGROUND,true,true),
    BOULDER(ConsoleColors.BLACK_BACKGROUND,true,true),
    STONE(ConsoleColors.BLACK_BACKGROUND,false,false),
    IRON(ConsoleColors.RED_BACKGROUND,true,true),
    GRASS(ConsoleColors.GREEN_BACKGROUND,true,true),
    MEADOW(ConsoleColors.PURPLE_BACKGROUND,true,true),
    DENSE_MEADOW(ConsoleColors.PURPLE_BACKGROUND,true,true),
    OIL(ConsoleColors.BLACK_BACKGROUND,false,false),
    PLAIN(ConsoleColors.BLACK_BACKGROUND,false,true),
    SHALLOW_WATER(ConsoleColors.YELLOW_BACKGROUND,false,true),
    RIVER(ConsoleColors.BLUE_BACKGROUND,false,false),
    SMALL_POND(ConsoleColors.WHITE_BACKGROUND,false,false),
    BIG_POND(ConsoleColors.WHITE_BACKGROUND,false,false),
    BEACH(ConsoleColors.YELLOW_BACKGROUND,false,false),
    SEA(ConsoleColors.BLUE_BACKGROUND,false,false),

    ;
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
