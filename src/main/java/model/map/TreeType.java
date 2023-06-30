package model.map;

public enum TreeType {
    THISTLE(ConsoleColors.YELLOW_BACKGROUND, "thistle"),
    CHERRY(ConsoleColors.RED_BACKGROUND, "cherry"),
    OLIVE(ConsoleColors.GREEN_BACKGROUND, "olive"),
    COCONUT(ConsoleColors.WHITE_BACKGROUND, "coconut"),
    DATES(ConsoleColors.BLACK_BACKGROUND, "dates"),
    ;
    private final ConsoleColors showingColor;
    private final String name;
    private final String showingImagePath;

    TreeType(ConsoleColors showingColor, String name) {
        this.name = name;
        this.showingColor = showingColor;
        this.showingImagePath = "/images/trees/" + name + ".png";
    }

    public static TreeType getTreeTypeByName(String name) {
        for (TreeType treeType : TreeType.values()) {
            if (treeType.name.equals(name)) return treeType;
        }
        return null;
    }

    public String getShowingImagePath() {
        return showingImagePath;
    }

    public ConsoleColors getShowingColor() {
        return showingColor;
    }

    public String getName() {
        return name;
    }
}
