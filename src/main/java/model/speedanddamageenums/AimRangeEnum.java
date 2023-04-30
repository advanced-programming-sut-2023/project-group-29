package model.speedanddamageenums;

public enum AimRangeEnum {
    ON_Cell(0),
    NON_ARCHER(1),
    SHORT_RANGE_ARCHER(4),
    LONG_RANGE_ARCHER(7);

    private final int range;
    AimRangeEnum(int range)
    {
        this.range =range;
    }

    public int getRange() {
        return range;
    }
}
