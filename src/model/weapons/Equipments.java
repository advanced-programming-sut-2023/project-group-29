package model.weapons;

public class Equipments extends Weapon {
    private final EquipmentsType equipmentsType;

    public Equipments(EquipmentsType equipmentsType) {
        super(equipmentsType.getWeaponTypes());

        this.equipmentsType = equipmentsType;
    }
}
