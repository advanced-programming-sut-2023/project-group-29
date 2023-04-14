package model.weapons;

public enum EquipmentsType
{
    ;
    private WeaponTypes weaponTypes;
    private EquipmentsType(WeaponTypes weaponTypes)
    {
        this.weaponTypes=weaponTypes;
    }

    public WeaponTypes getWeaponTypes()
    {
        return weaponTypes;
    }
}
