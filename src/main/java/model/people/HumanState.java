package model.people;

public enum HumanState {
    STANDING("standing"),
    DEFENSIVE("defensive"),
    OFFENSIVE("offensive");

    private final String string;
    HumanState(String string)
    {
        this.string=string;
    }
    public String getString() {
        return string;
    }

    public static HumanState getHumanStateByString(String string)
    {
        for(HumanState humanState:HumanState.values())
            if(humanState.string.equals(string))
                return humanState;

        return null;
    }
}
