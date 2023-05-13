package model.weapons.weaponTypes;

import model.speedanddamageenums.AimRangeEnum;
import model.speedanddamageenums.SpeedEnum;
import model.weapons.Weapon;
import model.weapons.WeaponTypes;

public enum OffensiveWeaponsType {
    GATE_DESTROYER(new WeaponTypes("gateDestroyer",Weapon.BuilderType.ENGINEER, 50, 4),SpeedEnum.TOO_SLOW, 100,AimRangeEnum.NON_ARCHER),
    CATAPULT(new WeaponTypes("catapult",Weapon.BuilderType.ENGINEER, 50, 2),SpeedEnum.TOO_SLOW, 70,AimRangeEnum.SHORT_CATAPULT_RANGE),
    FIRE_STONE_THROWER(new WeaponTypes("fireStoneThrower",Weapon.BuilderType.ENGINEER, 50, 3),SpeedEnum.TOO_SLOW, 90,AimRangeEnum.LONG_RANGE_ARCHER);

    private final int damage;
    private final int speed;
    private final int aimRange;
    private final WeaponTypes weaponTypes;

    OffensiveWeaponsType(WeaponTypes weaponTypes,SpeedEnum speed, int damage, AimRangeEnum aimRange) {
        this.weaponTypes = weaponTypes;
        this.speed=speed.getSpeedValue();
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

    public int getSpeed() {
        return speed;
    }
    public String getName() {
        return weaponTypes.name();
    }
}
