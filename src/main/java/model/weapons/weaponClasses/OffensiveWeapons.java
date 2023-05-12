package model.weapons.weaponClasses;

import controller.menucontrollers.GameMenuController;
import model.*;
import model.buildings.buildingClasses.AttackingBuilding;
import model.map.Cell;
import model.map.Map;
import model.UnitState;
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
    private UnitState unitState = UnitState.STANDING;


    public OffensiveWeapons(OffensiveWeaponsType offensiveWeaponsType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(offensiveWeaponsType.getWeaponTypes(), playerNumber, positionX, positionY);

        this.offensiveWeaponsType = offensiveWeaponsType;
        this.speed=offensiveWeaponsType.getSpeed();
        this.damage = offensiveWeaponsType.getDamage();
        this.aimRange = offensiveWeaponsType.getAimRange();
    }

    public MovingResult move(Map map, int destinationX, int destinationY) {
        return Movable.move(map, this, destinationX, destinationY);
    }

    public MovingResult checkForMoveErrors(Map map, int destinationX, int destinationY) {
        return Movable.checkForMoveErrors(map, this, destinationX, destinationY);
    }

    public AttackingResult canAttack(Map map, int targetX, int targetY,boolean setHasAttacked) {
        return Offensive.canAttack(map, this, targetX, targetY,setHasAttacked);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    public int getAimRange() {
        if(isArcherType()) {
            Cell currentCell= GameMenuController.getGameData().getMap().getCells()[getPositionX()][getPositionY()];
            if(currentCell.hasBuilding() && currentCell.getBuilding() instanceof AttackingBuilding attackingBuilding)
                return Math.max(aimRange, attackingBuilding.getFireRange());
        }
        return aimRange;
    }

    public boolean isArcherType() {
        //TODO abbasfar
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

    @Override
    public UnitState getUnitState() {
        return unitState;
    }

    @Override
    public void setUnitState(UnitState unitState) {
        this.unitState = unitState;
    }
    public boolean isAbleToClimbStairs() {
        return false;
    }
    public boolean isAbleToClimbLadder() {
        return false;
    }

}
