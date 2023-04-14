package model.weapons;

public enum EquipmentsType {
    ;
    private final WeaponTypes weaponTypes;

    EquipmentsType(WeaponTypes weaponTypes) {
        this.weaponTypes = weaponTypes;
    }

    public WeaponTypes getWeaponTypes() {
        return weaponTypes;
    }
}
