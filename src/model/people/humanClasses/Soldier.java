package model.people.humanClasses;

import model.PlayerNumber;
import model.people.Human;
import model.people.humanTypes.SoldierType;

public class Soldier extends Human {
    private final SoldierType soldierType;
    private final int damage;
    private final int aimRange;

    public Soldier(SoldierType soldierType, PlayerNumber playerNumber,int positionX,int positionY) {
        super(soldierType.getHumanType(), playerNumber, positionX, positionY);

        this.soldierType = soldierType;
        this.damage = soldierType.getDamage();
        this.aimRange = soldierType.getAimRange();
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
