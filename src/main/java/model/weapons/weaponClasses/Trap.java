package model.weapons.weaponClasses;

import model.Offensive;
import model.PlayerNumber;
import model.weapons.Weapon;
import model.weapons.weaponTypes.TrapType;

public class Trap extends Weapon{
    private final int damage;
    private final TrapType trapType;
    private boolean attackedThisTurn=false;


    public Trap(TrapType trapType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(trapType.getWeaponTypes(), playerNumber, positionX, positionY);

        this.trapType = trapType;
        this.damage = trapType.getDamage();
    }


    public int getDamage() {
        return damage;
    }

    public TrapType getTrapType() {
        return trapType;
    }

    public boolean isArcherType() {
        return false;
    }
    public boolean hasAttackedThisTurn()
    {
        return attackedThisTurn;
    }

    public void setAttackedThisTurn(boolean attackedThisTurn) {
        this.attackedThisTurn = attackedThisTurn;
    }
}
