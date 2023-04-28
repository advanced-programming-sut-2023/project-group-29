package model.weapons.weaponTypes;

import model.weapons.Weapon;
import model.weapons.WeaponTypes;

public enum TrapType {
    AMIN(new WeaponTypes(Weapon.BuilderType.ENGINEER, 1000, 1000, 100), 1000),
    AMIN2(null, 1000);
    private final int damage;
    private final WeaponTypes weaponTypes;

    TrapType(WeaponTypes weaponTypes, int damage) {
        this.weaponTypes = weaponTypes;
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public WeaponTypes getWeaponTypes() {
        return weaponTypes;
    }
}
