package model.map;

public enum ConsoleColors {
    RESET_COLOR("\u001B[0m"),
    BLACK_BACKGROUND("\u001B[40m"),
    RED_BACKGROUND("\u001B[41m"),
    GREEN_BACKGROUND("\u001B[42m"),
    YELLOW_BACKGROUND("\u001B[43m"),
    BLUE_BACKGROUND("\u001B[44m"),
    PURPLE_BACKGROUND("\u001B[45m"),
    CYAN_BACKGROUND("\u001B[46m"),
    WHITE_BACKGROUND("\u001B[47m");

    private final String stringCode;

    ConsoleColors(String stringCode) {
        this.stringCode = stringCode;
    }

    public String getStringCode() {
        return stringCode;
    }
}
