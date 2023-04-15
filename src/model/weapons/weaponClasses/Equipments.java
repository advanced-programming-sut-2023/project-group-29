package model.weapons.weaponClasses;

import model.PlayerNumber;
import model.weapons.weaponTypes.EquipmentsType;
import model.weapons.Weapon;

public class Equipments extends Weapon {
    private final EquipmentsType equipmentsType;

    public Equipments(EquipmentsType equipmentsType, PlayerNumber playerNumber) {
        super(equipmentsType.getWeaponTypes(), playerNumber);

        this.equipmentsType = equipmentsType;
    }
}
