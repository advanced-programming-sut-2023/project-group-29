package model.people.humanClasses;

import model.Map;
import model.Offensive;
import model.PlayerNumber;
import model.people.Human;
import model.people.humanTypes.SoldierType;

public class Soldier extends Human implements Offensive {
    private final SoldierType soldierType;
    private final int damage;
    private final int aimRange;

    //TODO reasonable value below
    private final int decreasingFactorOfShieldForArchers = 4;

    public Soldier(SoldierType soldierType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(soldierType.getHumanType(), playerNumber, positionX, positionY);

        this.soldierType = soldierType;
        this.damage = soldierType.getDamage();
        this.aimRange = soldierType.getAimRange();
    }

    public AttackingResult canAttack(Map map, int targetX, int targetY) {
        return Offensive.canAttack(map, this, aimRange, targetX, targetY);
    }

    public boolean isArcherType() {
        //TODO if(soldierType.equals(archer)) and all other archer types

        return true;
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
}
