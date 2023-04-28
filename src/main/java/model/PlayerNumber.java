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
        for (PlayerNumber playerNumber: PlayerNumber.values()) {
            if (playerNumber.number == index) return playerNumber;
        }
        return null;
    }

    public int getNumber() {
        return number;
    }
}
