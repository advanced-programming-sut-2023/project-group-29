package model.people;

import model.*;

public class Human extends Asset implements Movable
{
    enum State {
        OFFENSIVE,
        DEFENSIVE,
        STANDING
    }

    //TODO speed type slow, middle, fast and convert to number
    protected State state = State.STANDING;
    protected int hp;
    protected boolean ableToClimbLadder;
    protected int speed;
    private boolean patrolling;
    protected Human(HumanType humanType, PlayerNumber playerNumber,int positionX,int positionY) {
        super(playerNumber, positionX, positionY);
        this.hp = humanType.getHp();
        this.ableToClimbLadder = humanType.isAbleToClimbLadder();
        this.speed = humanType.getSpeed();
    }

    public MovingResult move(Map map, int currentX, int currentY, int destinationX, int destinationY)
    {
        if(destinationX<1 || destinationY<1 || destinationX> map.getWidth()|| destinationY> map.getWidth())
            return MovingResult.INVALID_INDEX;

        Cell currentCell=map.getCells()[currentX][currentY];
        if(currentCell.isAbleToMoveOn())
            return MovingResult.BAD_PLACE;
        if(!currentCell.getBuilding().getOwnerNumber().equals(this.getOwnerNumber()))
            return MovingResult.BAD_PLACE;

        int destinationDistance= map.distanceOfTwoCells(currentX,currentY,destinationX,destinationY,ableToClimbLadder);

        if(destinationDistance>speed)
            return MovingResult.TOO_FAR;

        return null;
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

    public boolean isPatrolling()
    {
        return patrolling;
    }

    public void setPatrolling(boolean patrolling)
    {
        this.patrolling = patrolling;
    }
}
