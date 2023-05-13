package model.map;

public enum TreeType {
    THISTLE(ConsoleColors.YELLOW_BACKGROUND, "thistle"),
    CHERRY(ConsoleColors.RED_BACKGROUND, "cherry"),
    OLIVE(ConsoleColors.GREEN_BACKGROUND, "olive"),
    COCONUT(ConsoleColors.WHITE_BACKGROUND, "coconut"),
    DATES(ConsoleColors.BLACK_BACKGROUND, "dates"),
    ;
    private final ConsoleColors showingColor;
    private String name;

    TreeType(ConsoleColors showingColor, String name) {
        this.name = name;
        this.showingColor = showingColor;
    }

    public static TreeType getTreeTypeByName(String name) {
        for (TreeType treeType : TreeType.values()) {
            if (treeType.name.equals(name)) return treeType;
        }
        return null;
    }

    public ConsoleColors getShowingColor() {
        return showingColor;
    }

}
