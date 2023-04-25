package model.weapons.weaponClasses;

import model.PlayerNumber;
import model.weapons.Weapon;
import model.weapons.weaponTypes.TrapType;

public class Trap extends Weapon {
    private final int damage;
    private final TrapType trapType;

    public Trap(TrapType trapType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(trapType.getWeaponTypes(), playerNumber, positionX, positionY);

        this.trapType = trapType;
        this.damage = trapType.getDamage();
    }
    //TODO implement trap with offensive or not
//    public Offensive.AttackingResult attack(Map map, int targetX, int targetY)
//    {
//        return Offensive.attack(map,this,0,targetX,targetY);
//    }

    public int getDamage() {
        return damage;
    }

    public TrapType getTrapType() {
        return trapType;
    }

    public boolean isArcherType() {
        //TODO
        return true;
    }
}
