package model.weapons.weaponClasses;

import model.PlayerNumber;
import model.map.Map;
import model.unitfeatures.Movable;
import model.unitfeatures.Patrol;
import model.weapons.Weapon;
import model.weapons.weaponTypes.EquipmentsType;

public class Equipments extends Weapon implements Movable {
    private final EquipmentsType equipmentsType;
    private final int speed;
    private final Patrol patrol = new Patrol();
    private boolean movedThisTurn = false;


    public Equipments(EquipmentsType equipmentsType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(equipmentsType.getWeaponTypes(), playerNumber, positionX, positionY);

        this.equipmentsType = equipmentsType;
        this.speed = equipmentsType.getSpeed();
    }

    public MovingResult move(Map map, int destinationX, int destinationY) {
        return Movable.move(map, this, destinationX, destinationY);
    }

    public MovingResult checkForMoveErrors(Map map, int destinationX, int destinationY) {
        return Movable.checkForMoveErrors(map, this, destinationX, destinationY);
    }

    public EquipmentsType getEquipmentsType() {
        return equipmentsType;
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

    public boolean isAbleToClimbStairs() {
        return false;
    }

    public boolean isAbleToClimbLadder() {
        return false;
    }
}
