package model.people;

public class Soldier extends Human
{
    private SoldierType soldierType;
    private int damage;
    private int aimRange;

    public Soldier(SoldierType soldierType)
    {
        super(soldierType.getHumanType());

        this.soldierType = soldierType;
        this.damage = soldierType.getDamage();
        this.aimRange = soldierType.getAimRange();
    }

    public int getDamage()
    {
        return damage;
    }

    public int getAimRange()
    {
        return aimRange;
    }

    public SoldierType getSoldierType()
    {
        return soldierType;
    }
}
