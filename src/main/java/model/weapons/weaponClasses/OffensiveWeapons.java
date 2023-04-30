package model.weapons.weaponClasses;

import model.Movable;
import model.Offensive;
import model.PlayerNumber;
import model.map.Map;
import model.weapons.Weapon;
import model.weapons.weaponTypes.OffensiveWeaponsType;

public class OffensiveWeapons extends Weapon implements Movable, Offensive {
    private final int damage;
    private final int aimRange;
    private final int speed;
    private final OffensiveWeaponsType offensiveWeaponsType;

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
}
