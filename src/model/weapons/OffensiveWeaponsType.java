package model.weapons;

public enum OffensiveWeaponsType
{
    ;
    private int damage;
    private int aimRange;
    private WeaponTypes weaponTypes;
    private OffensiveWeaponsType(WeaponTypes weaponTypes,int damage,int aimRange)
    {
        this.weaponTypes=weaponTypes;
        this.damage=damage;
        this.aimRange=aimRange;
    }

    public int getDamage()
    {
        return damage;
    }

    public int getAimRange()
    {
        return aimRange;
    }

    public WeaponTypes getWeaponTypes()
    {
        return weaponTypes;
    }
}
