package model.weapons.weaponClasses;

import model.PlayerNumber;
import model.weapons.weaponTypes.TrapType;
import model.weapons.Weapon;

public class Trap extends Weapon {
    private final int damage;
    private final TrapType trapType;

    public Trap(TrapType trapType, PlayerNumber playerNumber,int positionX,int positionY) {
        super(trapType.getWeaponTypes(), playerNumber,positionX,positionY);

        this.trapType = trapType;
        this.damage = trapType.getDamage();
    }

    public int getDamage() {
        return damage;
    }

    public TrapType getTrapType() {
        return trapType;
    }
}
