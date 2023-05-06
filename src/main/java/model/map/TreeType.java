package model.map;

public enum TreeType {
    THISTLE(ConsoleColors.YELLOW_BACKGROUND),
    CHERRY(ConsoleColors.YELLOW_BACKGROUND),
    OLIVE(ConsoleColors.YELLOW_BACKGROUND),
    COCONUT(ConsoleColors.YELLOW_BACKGROUND),
    DATES(ConsoleColors.YELLOW_BACKGROUND),
    ;
    private final ConsoleColors showingColor;

    TreeType(ConsoleColors showingColor) {
        this.showingColor = showingColor;
    }

    public ConsoleColors getShowingColor() {
        return showingColor;
    }

}
