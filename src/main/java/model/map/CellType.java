package model.map;

public enum CellType {
    PLAIN_GROUND(ConsoleColors.YELLOW_BACKGROUND,true,true, "plain ground"),
    GROUND_WITH_PEBBLES(ConsoleColors.CYAN_BACKGROUND,true,true, "ground with pebbles"),
    BOULDER(ConsoleColors.BLACK_BACKGROUND,true,true, "boulder"),
    STONE(ConsoleColors.BLACK_BACKGROUND,false,false, "stone"),
    IRON(ConsoleColors.RED_BACKGROUND,true,true, "iron"),
    GRASS(ConsoleColors.GREEN_BACKGROUND,true,true, "grass"),
    MEADOW(ConsoleColors.PURPLE_BACKGROUND,true,true, "meadow"),
    DENSE_MEADOW(ConsoleColors.PURPLE_BACKGROUND,true,true, "dense meadow"),
    OIL(ConsoleColors.BLACK_BACKGROUND,false,false, "oil"),
    PLAIN(ConsoleColors.BLACK_BACKGROUND,false,true, "plain"),
    SHALLOW_WATER(ConsoleColors.YELLOW_BACKGROUND,false,true, "shallow water"),
    RIVER(ConsoleColors.BLUE_BACKGROUND,false,false, "river"),
    SMALL_POND(ConsoleColors.WHITE_BACKGROUND,false,false, "small pond"),
    BIG_POND(ConsoleColors.WHITE_BACKGROUND,false,false, "big pond"),
    BEACH(ConsoleColors.YELLOW_BACKGROUND,false,false, "beach"),
    SEA(ConsoleColors.BLUE_BACKGROUND,false,false, "sea"),

    ;
    //page 27 of doc
    ;
    private final ConsoleColors showingColor;
    private final boolean ableToBuildOn;
    private final boolean ableToMoveOn;
    private final String name;

    CellType(ConsoleColors showingColor, boolean ableToBuildOn, boolean ableToMoveOn, String name) {
        this.ableToBuildOn = ableToBuildOn;
        this.ableToMoveOn = ableToMoveOn;
        this.showingColor = showingColor;
        this.name = name;
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

    public String getName() {
        return name;
    }
}
