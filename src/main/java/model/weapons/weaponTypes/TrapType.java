package model.weapons.weaponTypes;

import model.weapons.Weapon;
import model.weapons.WeaponTypes;

public enum TrapType {
    KILLING_HOLE(new WeaponTypes("killingHole", Weapon.BuilderType.ENGINEER, 20, 1, "KLH","/images/units/weapons/hole.png"), 150); //todo image path is incorrect
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

    public String getName() {
        return weaponTypes.name();
    }
}
