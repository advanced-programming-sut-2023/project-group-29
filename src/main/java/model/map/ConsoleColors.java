package model.map;

public enum ConsoleColors {
    RED_BACKGROUND("\u001B[41m"),
    BLUE_BACKGROUND("\u001B[44m"),
    GREEN_BACKGROUND("\u001B[42m"),
    WHITE_BACKGROUND("\u001B[47m"),
    YELLOW_BACKGROUND("\u001B[43m"),
    RESET_COLOR("\u001B[0m");

    private final String stringCode;

    ConsoleColors(String stringCode) {
        this.stringCode = stringCode;
    }

    public String getStringCode() {
        return stringCode;
    }
}
