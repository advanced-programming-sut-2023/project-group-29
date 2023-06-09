package model.people;

import model.Asset;
import model.PlayerNumber;
import model.map.Map;
import model.people.humanClasses.Soldier;
import model.people.humanClasses.Worker;
import model.people.humanTypes.SoldierType;
import model.people.humanTypes.WorkerType;
import model.unitfeatures.Movable;
import model.unitfeatures.Patrol;

public class Human extends Asset implements Movable {
    private final String name;
    private final Patrol patrol = new Patrol();
    protected boolean ableToClimbLadder;
    protected boolean ableToClimbStairs;
    protected int speed;
    private boolean movedThisTurn = false;
    private boolean hasFire = false;

    protected Human(HumanType humanType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(playerNumber, positionX, positionY);
        this.hp = humanType.hp();
        this.ableToClimbLadder = humanType.ableToClimbLadder();
        this.speed = humanType.speed().getSpeedValue();
        this.showingSignInMap = humanType.showingSignInMap() + "__" + (playerNumber.getNumber() + 1);
        this.name = humanType.name();
        this.ableToClimbStairs = humanType.ableToClimbStairs();
        this.showingImageFilePath = humanType.showingImageFilePath();
    }

    public static Human createUnitByName(String unitName, PlayerNumber ownerNumber, int positionX, int positionY) {

        //if soldier
        for (SoldierType soldierType : SoldierType.values())
            if (soldierType.getName().equals(unitName)) {
                return new Soldier(soldierType, ownerNumber, positionX, positionY);
            }

        //if worker
        for (WorkerType workerType : WorkerType.values())
            if (workerType.getName().equals(unitName))
                return new Worker(workerType, ownerNumber, positionX, positionY);

        return null;
    }

    public MovingResult move(Map map, int destinationX, int destinationY) {
        return Movable.move(map, this, destinationX, destinationY);
    }

    public MovingResult checkForMoveErrors(Map map, int destinationX, int destinationY) {
        return Movable.checkForMoveErrors(map, this, destinationX, destinationY);
    }

    public boolean isAbleToClimbLadder() {
        return ableToClimbLadder;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean hasMovedThisTurn() {
        return movedThisTurn;
    }

    public void setMovedThisTurn(boolean movedThisTurn) {
        this.movedThisTurn = movedThisTurn;
    }

    @Override
    public Patrol getPatrol() {
        return patrol;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean isAbleToClimbStairs() {
        return ableToClimbStairs;
    }

    public boolean hasFire() {
        return hasFire;
    }

    public void setHasFire(boolean hasFire) {
        this.hasFire = hasFire;
    }
}
