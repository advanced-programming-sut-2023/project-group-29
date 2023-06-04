package model.unitfeatures;

public enum UnitState {
    STANDING("standing"),
    DEFENSIVE("defensive"),
    OFFENSIVE("offensive");

    private final String string;

    UnitState(String string) {
        this.string = string;
    }

    public static UnitState getUnitStateByString(String string) {
        for (UnitState unitState : UnitState.values())
            if (unitState.string.equals(string))
                return unitState;

        return null;
    }

    public String getString() {
        return string;
    }
}
