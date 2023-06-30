package model.weapons.weaponTypes;

import model.speedanddamageenums.AimRangeEnum;
import model.weapons.Weapon;
import model.weapons.WeaponTypes;

public enum StaticOffensiveWeaponsType {
    CATAPULT_WITH_BALLAST(new WeaponTypes("catapultWithBallast", Weapon.BuilderType.ENGINEER, 80, 3, "CPB", "/images/units/weapons/trebuchet.png"), 100, AimRangeEnum.LONG_CATAPULT_RANGE);
    private final int damage;
    private final int aimRange;
    private final WeaponTypes weaponTypes;

    StaticOffensiveWeaponsType(WeaponTypes weaponTypes, int damage, AimRangeEnum aimRange) {
        this.weaponTypes = weaponTypes;
        this.damage = damage;
        this.aimRange = aimRange.getRange();
    }

    public int getDamage() {
        return damage;
    }

    public int getAimRange() {
        return aimRange;
    }

    public WeaponTypes getWeaponTypes() {
        return weaponTypes;
    }

    public String getName() {
        return weaponTypes.name();
    }
}
