package model;

public enum PlayerNumber {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FORTH(4),
    FIFTH(5),
    SIXTH(6),
    SEVENTH(7),
    EIGHTH(8);

    private final int number;

    PlayerNumber(int number) {
        this.number = number;
    }

    public static PlayerNumber getPlayerByIndex(int index) {
        switch (index) {
            case 1:
                return FIRST;
            case 2:
                return SECOND;
            case 3:
                return THIRD;
            case 4:
                return FORTH;
            case 5:
                return FIFTH;
            case 6:
                return SIXTH;
            case 7:
                return SEVENTH;
            case 8:
                return EIGHTH;
        }
        return null;
    }

    public int getNumber() {
        return number;
    }
}
