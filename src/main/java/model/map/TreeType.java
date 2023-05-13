package model.map;

public enum TreeType {
    THISTLE(ConsoleColors.YELLOW_BACKGROUND),
    CHERRY(ConsoleColors.RED_BACKGROUND),
    OLIVE(ConsoleColors.GREEN_BACKGROUND),
    COCONUT(ConsoleColors.WHITE_BACKGROUND),
    DATES(ConsoleColors.BLACK_BACKGROUND),
    ;
    private final ConsoleColors showingColor;

    TreeType(ConsoleColors showingColor) {
        this.showingColor = showingColor;
    }

    public ConsoleColors getShowingColor() {
        return showingColor;
    }

}
