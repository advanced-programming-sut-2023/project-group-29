package model;

public enum Direction {
    RIGHT("right"),
    DOWN("down"),
    LEFT("left"),
    UP("up");
    private final String string;

    Direction(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
