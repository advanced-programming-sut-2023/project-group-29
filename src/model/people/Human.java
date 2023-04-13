package model.people;

public abstract class Human {
    enum State
    {
        OFFENSIVE,
        DEFENSIVE,
        STANDING
    }
    protected State state=State.STANDING;
    protected int hp;
    protected boolean ableToClimbLadder;

    public int getHp()
    {
        return hp;
    }

    public boolean isAbleToClimbLadder()
    {
        return ableToClimbLadder;
    }

    public State getState()
    {
        return state;
    }
    public void setState(State state)
    {
        this.state = state;
    }
    public void changeHp(int amount)
    {
        hp+=amount;
    }
    public boolean isAlive()
    {
        return hp>0;
    }
}
