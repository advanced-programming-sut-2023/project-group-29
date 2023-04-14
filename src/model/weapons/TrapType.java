package model.weapons;

public enum TrapType
{
    AMIN(new WeaponTypes(Weapon.BuilderType.ENGINEER,1000,1000,100),1000);
    private int damage;
    private WeaponTypes weaponTypes;
    private TrapType(WeaponTypes weaponTypes,int damage)
    {
        this.weaponTypes=weaponTypes;
        this.damage=damage;
    }
    public int getDamage()
    {
        return damage;
    }

    public WeaponTypes getWeaponTypes()
    {
        return weaponTypes;
    }
}
