package model.weapons.weaponClasses;

import model.Movable;
import model.Offensive;
import model.PlayerNumber;
import model.map.Map;
import model.weapons.Weapon;
import model.weapons.weaponTypes.OffensiveWeaponsType;
import model.weapons.weaponTypes.StaticOffensiveWeaponsType;

public class StaticOffensiveWeapons extends Weapon implements Offensive {
    private final int damage;
    private final int aimRange;
    private final StaticOffensiveWeaponsType staticOffensiveWeaponsType;

    public StaticOffensiveWeapons(StaticOffensiveWeaponsType staticOffensiveWeaponsType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(staticOffensiveWeaponsType.getWeaponTypes(), playerNumber, positionX, positionY);

        this.staticOffensiveWeaponsType = staticOffensiveWeaponsType;
        this.damage = staticOffensiveWeaponsType.getDamage();
        this.aimRange = staticOffensiveWeaponsType.getAimRange();
    }

    public Offensive.AttackingResult canAttack(Map map, int targetX, int targetY) {
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
        return true;
    }

    public StaticOffensiveWeaponsType getStaticOffensiveWeaponsType() {
        return staticOffensiveWeaponsType;
    }
}
