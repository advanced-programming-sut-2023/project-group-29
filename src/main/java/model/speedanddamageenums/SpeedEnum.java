package model.speedanddamageenums;

public enum SpeedEnum {
    TOO_SLOW(2),
    SLOW(3),
    NORMAL(5),
    FAST(7),
    TOO_FAST(9);
    private final int speedValue;

    SpeedEnum(int speedValue) {
        this.speedValue = speedValue;
    }

    public int getSpeedValue() {
        return speedValue;
    }
}
