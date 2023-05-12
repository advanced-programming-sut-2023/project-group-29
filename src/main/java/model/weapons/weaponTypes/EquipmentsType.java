package model.weapons.weaponTypes;

import model.speedanddamageenums.SpeedEnum;
import model.weapons.Weapon;
import model.weapons.WeaponTypes;

public enum EquipmentsType {
    PORTABLE_SHIELD(new WeaponTypes("portableShield",Weapon.BuilderType.ENGINEER, 100, 1),SpeedEnum.NORMAL),
    SIEGE_TOWER(new WeaponTypes("siegeTower",Weapon.BuilderType.ENGINEER, 50, 4),SpeedEnum.TOO_SLOW);

    private final WeaponTypes weaponTypes;
    private final int speed;

    EquipmentsType(WeaponTypes weaponTypes, SpeedEnum speed) {
        this.weaponTypes = weaponTypes;
        this.speed=speed.getSpeedValue();
    }

    public WeaponTypes getWeaponTypes() {
        return weaponTypes;
    }

    public int getSpeed() {
        return speed;
    }
}
