package model.weapons.weaponClasses;

import model.*;
import model.map.Map;
import model.weapons.Weapon;
import model.weapons.weaponTypes.OffensiveWeaponsType;

public class OffensiveWeapons extends Weapon implements Movable, Offensive {
    private final int damage;
    private final int aimRange;
    private final int speed;
    private final OffensiveWeaponsType offensiveWeaponsType;
    private boolean movedThisTurn=false;
    private boolean attackedThisTurn=false;
    private final Patrol patrol=new Patrol();

    public OffensiveWeapons(OffensiveWeaponsType offensiveWeaponsType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(offensiveWeaponsType.getWeaponTypes(), playerNumber, positionX, positionY);

        this.offensiveWeaponsType = offensiveWeaponsType;
        this.speed=offensiveWeaponsType.getSpeed();
        this.damage = offensiveWeaponsType.getDamage();
        this.aimRange = offensiveWeaponsType.getAimRange();
    }

    public MovingResult move(Map map, int destinationX, int destinationY) {
        return Movable.move(map, this, speed, false, destinationX, destinationY);
    }

    public MovingResult checkForMoveErrors(Map map, int destinationX, int destinationY) {
        return Movable.checkForMoveErrors(map, this, speed, false, destinationX, destinationY);
    }

    public AttackingResult canAttack(Map map, int targetX, int targetY) {
        return Offensive.canAttack(map, this, aimRange, targetX, targetY);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    public int getAimRange() {
        return aimRange;
    }

    public boolean isArcherType() {
        //TODO
        return true;
    }

    public OffensiveWeaponsType getOffensiveWeaponsType() {
        return offensiveWeaponsType;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean hasMovedThisTurn()
    {
        return movedThisTurn;
    }
    public boolean hasAttackedThisTurn()
    {
        return attackedThisTurn;
    }

    public void setAttackedThisTurn(boolean attackedThisTurn) {
        this.attackedThisTurn = attackedThisTurn;
    }

    public void setMovedThisTurn(boolean movedThisTurn) {
        this.movedThisTurn = movedThisTurn;
    }

    @Override
    public Patrol getPatrol() {
        return patrol;
    }
}
