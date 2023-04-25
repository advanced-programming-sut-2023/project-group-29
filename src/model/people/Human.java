package model.people;

import model.Asset;
import model.Map;
import model.Movable;
import model.PlayerNumber;

public class Human extends Asset implements Movable {
    //TODO speed type slow, middle, fast and convert to number
    protected State state = State.STANDING;
    protected boolean ableToClimbLadder;
    protected int speed;
    private boolean patrolling;
    protected Human(HumanType humanType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(playerNumber, positionX, positionY);
        this.hp = humanType.getHp();
        this.ableToClimbLadder = humanType.isAbleToClimbLadder();
        this.speed = humanType.getSpeed();
    }

    public MovingResult move(Map map, int destinationX, int destinationY) {
        return Movable.move(map, this, speed, ableToClimbLadder, destinationX, destinationY);
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

    public boolean isAlive() {
        return hp > 0;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isPatrolling() {
        return patrolling;
    }

    public void setPatrolling(boolean patrolling) {
        this.patrolling = patrolling;
    }

    enum State {
        OFFENSIVE,
        DEFENSIVE,
        STANDING
    }
}
