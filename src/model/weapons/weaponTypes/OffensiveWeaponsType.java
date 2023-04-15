package model.weapons.weaponTypes;

import model.weapons.WeaponTypes;

public enum OffensiveWeaponsType {
    ;
    private final int damage;
    private final int aimRange;
    private final WeaponTypes weaponTypes;

    OffensiveWeaponsType(WeaponTypes weaponTypes, int damage, int aimRange) {
        this.weaponTypes = weaponTypes;
        this.damage = damage;
        this.aimRange = aimRange;
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
}
