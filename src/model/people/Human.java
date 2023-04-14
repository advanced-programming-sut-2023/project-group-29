package model.people;

public class Human {
    protected State state = State.STANDING;
    protected int hp;
    protected boolean ableToClimbLadder;
    protected int speed;
    protected Human(HumanType humanType) {
        this.hp = humanType.getHp();
        this.ableToClimbLadder = humanType.isAbleToClimbLadder();
        this.speed = humanType.getSpeed();
    }

    public int getHp() {
        return hp;
    }

    public boolean isAbleToClimbLadder() {
        return ableToClimbLadder;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void changeHp(int amount) {
        hp += amount;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public int getSpeed() {
        return speed;
    }

    enum State {
        OFFENSIVE,
        DEFENSIVE,
        STANDING
    }
}
