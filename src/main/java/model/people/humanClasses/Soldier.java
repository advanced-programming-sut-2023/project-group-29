package model.people.humanClasses;

import model.Offensive;
import model.PlayerNumber;
import model.map.Map;
import model.people.Human;
import model.people.UnitState;
import model.people.humanTypes.SoldierType;

public class Soldier extends Human implements Offensive {
    private final SoldierType soldierType;
    private final int damage;
    private final int aimRange;
    private UnitState unitState = UnitState.STANDING;

    //TODO reasonable value below
    private final int decreasingFactorOfShieldForArchers = 4;
    private boolean attackedThisTurn=false;


    public Soldier(SoldierType soldierType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(soldierType.getHumanType(), playerNumber, positionX, positionY);

        this.soldierType = soldierType;
        this.damage = soldierType.getAttackDamage();
        this.aimRange = soldierType.getAimRange();
    }

    public AttackingResult canAttack(Map map, int targetX, int targetY) {
        return Offensive.canAttack(map, this, aimRange, targetX, targetY);
    }

    public boolean isArcherType() {
        return switch (soldierType) {
            case ARCHER, CROSSBOWMAN, ARCHER_BOW, SLINGER, HORSE_ARCHER, FIRE_THROWER -> true;
            default -> false;
        };
    }

    public int getDamage() {
        return damage;
    }

    public int getAimRange() {
        return aimRange;
    }

    public SoldierType getSoldierType() {
        return soldierType;
    }
    public boolean hasAttackedThisTurn()
    {
        return attackedThisTurn;
    }

    public UnitState getUnitState() {
        return unitState;
    }

    public void setUnitState(UnitState unitState) {
        this.unitState = unitState;
    }

    public void setAttackedThisTurn(boolean attackedThisTurn) {
        this.attackedThisTurn = attackedThisTurn;
    }
}
