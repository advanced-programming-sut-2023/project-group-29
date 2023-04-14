package model.weapons;

public class Trap extends Weapon
{
    private int damage;
    private TrapType trapType;
    public Trap(TrapType trapType)
    {
        super(trapType.getWeaponTypes());

        this.trapType = trapType;
        this.damage=trapType.getDamage();
    }

    public int getDamage()
    {
        return damage;
    }

    public TrapType getTrapType()
    {
        return trapType;
    }
}
