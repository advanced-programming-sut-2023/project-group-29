package model.weapons.weaponTypes;

import model.weapons.Weapon;
import model.weapons.WeaponTypes;

public enum TrapType {
    KILLING_HOLE(new WeaponTypes(Weapon.BuilderType.ALL, 20, 100), 100);
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
