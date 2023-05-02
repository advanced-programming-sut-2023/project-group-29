package model.people;

import model.Asset;
import model.Movable;
import model.Patrol;
import model.PlayerNumber;
import model.map.Map;

public class Human extends Asset implements Movable {
    //TODO speed type slow, middle, fast and convert to number

    //todo defense damage should differ
    protected HumanState humanState = HumanState.STANDING;
    protected boolean ableToClimbLadder;
    protected int speed;
    private boolean movedThisTurn=false;

    private final Patrol patrol=new Patrol();


    protected Human(HumanType humanType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(playerNumber, positionX, positionY);
        this.hp = humanType.hp();
        this.ableToClimbLadder = humanType.ableToClimbLadder();
        this.speed = humanType.speed().getSpeedValue();
        this.showingSignInMap=humanType.showingSignInMap();
    }

    public MovingResult move(Map map, int destinationX, int destinationY) {
        return Movable.move(map, this, speed, ableToClimbLadder, destinationX, destinationY);
    }
    public MovingResult checkForMoveErrors(Map map, int destinationX, int destinationY) {
        return Movable.checkForMoveErrors(map, this, speed, ableToClimbLadder, destinationX, destinationY);
    }

    public boolean isAbleToClimbLadder() {
        return ableToClimbLadder;
    }

    public HumanState getState() {
        return humanState;
    }

    public void setState(HumanState humanState) {
        this.humanState = humanState;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean hasMovedThisTurn()
    {
        return movedThisTurn;
    }

    public void setMovedThisTurn(boolean movedThisTurn) {
        this.movedThisTurn = movedThisTurn;
    }

    @Override
    public Patrol getPatrol() {
        return patrol;
    }
}
