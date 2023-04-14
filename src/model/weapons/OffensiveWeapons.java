package model.weapons;

public class OffensiveWeapons extends Weapon
{
    private int damage;
    private int aimRange;
    private OffensiveWeaponsType offensiveWeaponsType;

    public OffensiveWeapons(OffensiveWeaponsType offensiveWeaponsType)
    {
        super(offensiveWeaponsType.getWeaponTypes());

        this.offensiveWeaponsType = offensiveWeaponsType;
        this.damage=offensiveWeaponsType.getDamage();
        this.aimRange=offensiveWeaponsType.getAimRange();
    }
}
