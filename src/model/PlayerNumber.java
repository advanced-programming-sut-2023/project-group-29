package model;

public enum PlayerNumber
{
    FIRST(0),
    SECOND(1),
    THIRD(2),
    FORTH(3),
    FIFTH(4),
    SIXTH(5),
    SEVENTH(6),
    EIGHTH(7);

    private int number;

    PlayerNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
