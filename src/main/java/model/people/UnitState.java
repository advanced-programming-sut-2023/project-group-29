package model.people;

public enum UnitState {
    //todo move outside the folder
    STANDING("standing"),
    DEFENSIVE("defensive"),
    OFFENSIVE("offensive");

    private final String string;
    UnitState(String string)
    {
        this.string=string;
    }
    public String getString() {
        return string;
    }

    public static UnitState getUnitStateByString(String string)
    {
        for(UnitState unitState : UnitState.values())
            if(unitState.string.equals(string))
                return unitState;

        return null;
    }
}
